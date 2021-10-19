package com.jjtest.user.groupchart;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * 群聊客户端
 * 1、发送消息到服务器
 * 2、读取服务器转发的其它用户发送的消息
 */
public class GroupChartClient {

    private SocketChannel socketChannel;

    private Selector selector;

    private static final String IP = "127.0.0.1";

    private static final int PORT = 6667;

    private String userName ;

    public GroupChartClient() {
        try {
            // ip 端口
            InetSocketAddress inetSocketAddress = new InetSocketAddress(IP, PORT);
            //创建 socket渠道
            socketChannel = socketChannel.open(inetSocketAddress);
            //设置为非阻塞
            socketChannel.configureBlocking(false);

            //创建选择器
            selector = Selector.open();
            //将socket渠道注册到selector
            socketChannel.register(selector, SelectionKey.OP_READ);
            userName = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println(socketChannel.getLocalAddress() + "  is ok");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息
     * @param msg
     * @throws Exception
     */
    private void sendMsg(String msg) throws Exception{
        msg = userName + "发送:" + msg;
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());

        socketChannel.write(buffer);
    }

    /**
     * 读取消息
     * @throws Exception
     */
    private void showMsg() throws Exception{
        //获取有事件的通道数  阻塞， selectNow() 非阻塞，马上返回结果  select(long ) 等待一段时间，没有事件发生，返回
        int count = selector.select();
        if(count == 0) {
            return;
        }
        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            if(key.isReadable()) {
                SocketChannel channel =(SocketChannel) key.channel();
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                channel.read(buffer);
                System.out.println(new String(buffer.array()));
            }
            //移除当前key 避免重复处理
            iterator.remove();
        }
    }

    public static void main(String[] args) throws Exception{
        GroupChartClient groupChartClient = new GroupChartClient();

        //创建线程读取消息
        new Thread(()->{
            try {
                while (true) {
                    groupChartClient.showMsg();
                    Thread.currentThread().sleep(3000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();

        // 发送消息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.next();
            groupChartClient.sendMsg(s);
        }


    }

}
