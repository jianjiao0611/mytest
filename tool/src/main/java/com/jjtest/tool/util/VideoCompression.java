package com.jjtest.tool.util;

import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;
import ws.schild.jave.info.AudioInfo;
import ws.schild.jave.info.VideoInfo;
import ws.schild.jave.info.VideoSize;

import java.io.File;
import java.math.BigDecimal;
import java.util.Optional;

//import ws.schild.jave.encode.AudioAttributes;
//import ws.schild.jave.encode.EncodingAttributes;
//import ws.schild.jave.encode.VideoAttributes;
//import ws.schild.jave.info.AudioInfo;
//import ws.schild.jave.info.VideoSize;

public class VideoCompression {

    public static void main(String[] args) {
        File source = new File("C:\\Users\\jiaojiao\\Videos\\李天涯\\hebg.mp4");
        File target = new File("C:\\Users\\jiaojiao\\Videos\\target15.mp4");
//        long size = FileUtils.sizeOfDirectory(source);
//        System.out.println(size);
        System.out.println(Math.ceil(source.length() >> 20));
        VideoCompression.run(source, target);
//        double mb = Math.ceil(104857612 / 1048576);
//        System.out.println(mb);
//        double ceil = Math.ceil(104857612 >> 20);
//        System.out.println(ceil);
    }

    /**
     * 传视频File对象，返回压缩后File对象信息
     *
     * @param source
     */
    public static void run(File source, File target) {
        try {
            MultimediaObject object = new MultimediaObject(source);
            AudioInfo audioInfo = object.getInfo().getAudio();
            // 根据视频⼤⼩来判断是否需要进⾏压缩,
            int maxSize = 5;
            double mb = Math.ceil(source.length() >> 20);
            int second = (int) object.getInfo().getDuration() / 1000;
            BigDecimal bd = new BigDecimal(String.format("%.4f", mb / second));
            System.out.println("开始压缩视频了--> 视频每秒平均 " + bd + " MB ");
            // 视频 > 5MB, 或者每秒 > 0.5 MB 才做压缩，不需要的话可以把判断去掉
            boolean temp = mb > maxSize || bd.compareTo(new BigDecimal(0.5)) > 0;
            if (true) {
                long time = System.currentTimeMillis();
                // TODO 视频属性设置
                //{"audioMaxBitRate":12800,"audioMaxSamplingRate":4410,"videoMaxBitRate":24000,"maxFrameRate":20,"maxWidth":1280}
                //{"audioMaxBitRate":128000,"audioMaxSamplingRate":44100,"videoMaxBitRate":2400000,"maxFrameRate":20,"maxWidth":1280}
                //{"audioMaxBitRate":128000,"audioMaxSamplingRate":88200,"videoMaxBitRate":960000,"maxFrameRate":200,"maxWidth":1280,"minFileSize":5}
                int maxBitRate = 128000;
                int maxSamplingRate = 88200;
                int bitRate = 960000;
                int maxFrameRate = 200;
                int maxWidth = 1280;
                AudioAttributes audio = new AudioAttributes();
                // 设置通⽤编码格式10 audio.setCodec("aac");
                // 设置最⼤值：⽐特率越⾼，清晰度/⾳质越好
                // 设置⾳频⽐特率,单位:b (⽐特率越⾼，清晰度/⾳质越好，当然⽂件也就越⼤ 128000 = 182kb)
                if (audioInfo.getBitRate() > maxBitRate) {
                    audio.setBitRate(new Integer(maxBitRate));
                }
                // 设置重新编码的⾳频流中使⽤的声道数（1 =单声道，2 = 双声道（⽴体声））。如果未设置任何声道值，则编码器将选择默认值 0。
                audio.setChannels(audioInfo.getChannels());
                // 采样率越⾼声⾳的还原度越好，⽂件越⼤
                // 设置⾳频采样率，单位：赫兹 hz
                // 设置编码时候的⾳量值，未设置为0,如果256，则⾳量值不会改变
                // audio.setVolume(256);
                if (audioInfo.getSamplingRate() > maxSamplingRate) {
                    audio.setSamplingRate(maxSamplingRate);
                }
                // TODO 视频编码属性配置
                VideoInfo videoInfo = object.getInfo().getVideo();
                VideoAttributes video = new VideoAttributes();
                video.setCodec("h264");
                // 设置⾳频⽐特率,单位:b (⽐特率越⾼，清晰度/⾳质越好，当然⽂件也就越⼤ 800000 = 800kb)
                if (videoInfo.getBitRate() > bitRate) {
                    video.setBitRate(bitRate);
                }
                // 视频帧率：15 f / s 帧率越低，效果越差
                // 设置视频帧率（帧率越低，视频会出现断层，越⾼让⼈感觉越连续），视频帧率（Frame
                // rate）是⽤于测量显⽰帧数的量度。所谓的测量单位为每秒显⽰帧数(Frames per Second，简：FPS）或“赫兹”（Hz）。
                if (videoInfo.getFrameRate() > maxFrameRate) {
                    video.setFrameRate(maxFrameRate);
                }
                // 限制视频宽⾼
                int width = videoInfo.getSize().getWidth();
                int height = videoInfo.getSize().getHeight();
                if (width > maxWidth) {
                    float rat = (float) width / maxWidth;
                    video.setSize(new VideoSize(maxWidth, (int) (height / rat)));
                }
                EncodingAttributes attr = new EncodingAttributes();
                // attr.setFormat("mp4");
                attr.setAudioAttributes(audio);
                attr.setVideoAttributes(video);
                // 速度最快的压缩⽅式，压缩速度从快到慢： ultrafast, superfast, veryfast, faster, fast, medium,
                // slow, slower, veryslow and placebo.
//                 attr.setPreset(PresetUtil.VERYFAST);
//                 attr.setCrf(27);
                // 设置线程数
                Optional<Integer> encodingThreads = attr.getEncodingThreads();
                int th = 0;
                if (encodingThreads.isPresent()) {
                    th = encodingThreads.get();
                }
                System.out.println("th---------------》"  + th);
                attr.setEncodingThreads(10);
                Encoder encoder = new Encoder();
                encoder.encode(new MultimediaObject(source), target, attr);
                System.out.println("压缩总耗时：" + (System.currentTimeMillis() - time) / 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
