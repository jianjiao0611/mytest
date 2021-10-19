package com.jjtest.user.groupchart;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * 群聊服务器
 * 1、用户发送消息到服务器
 * 2、服务器转发消息到其它用户
 */
public class GroupChartServer {

    private ServerSocketChannel serverSocketChannel;

    private Selector selector;

    private static final int PORT = 6667;

    /**
     * 初始化
     */
    public GroupChartServer() {
        try {
            //创建serverSocket
            serverSocketChannel = ServerSocketChannel.open();
            //创建选择器 监听渠道事件
            selector = Selector.open();
            //设置为非阻塞
            serverSocketChannel.configureBlocking(false);
            InetSocketAddress inetSocketAddress = new InetSocketAddress(PORT);
            //绑定ip 端口
            serverSocketChannel.bind(inetSocketAddress);
            //将渠道注册到selector
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听
     */
    private void listen() {
        SocketChannel channel = null;
        try {
            while (true) {
                int count = selector.select();
                if(count == 0) {
                    return;
                }
                //有事件
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    //注册事件
                    if(key.isAcceptable()) {
                        //获取一个渠道用于和客户端数据交互
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        //设置为非阻塞
                        socketChannel.configureBlocking(false);
                        //将socketChannel注册到 selector 注册时将buffer放入selectionKey,之后可通过key反向获取buffer
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        System.out.println(socketChannel.getRemoteAddress() + " 上线 ");
                    }
                    //读取事件
                    if(key.isReadable()) {
                        //反向获取渠道
                        channel = (SocketChannel)key.channel();

                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        channel.read(buffer);
                        System.out.println(new String(buffer.array()));

                        //转发
                        this.sendMsgOther(new String(buffer.array()), channel);
                    }

                    //移除当前key 避免重复处理
                    iterator.remove();
                }
            }
        } catch (Exception e) {
            try {
                System.out.println(channel.getRemoteAddress() + " 下线");
                channel.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }


    }

    /**
     * 转发消息
     * @param msg 消息
     * @param self 当前用户渠道
     * @throws Exception
     */
    private void sendMsgOther(String msg, SocketChannel self) throws Exception{
        Iterator<SelectionKey> iterator = selector.keys().iterator();

        while (iterator.hasNext()) {
            SelectionKey key = iterator.next();
            Channel channel = key.channel();
            if(channel instanceof SocketChannel && channel != self) {
                //转发
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                ((SocketChannel) channel).write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        GroupChartServer groupChartServer = new GroupChartServer();
        groupChartServer.listen();
    }





}
