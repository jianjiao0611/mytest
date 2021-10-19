package com.jjtest.user;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClientTest {

    public static void main(String[] args) {

        try {
            //创建socketChannel
            SocketChannel socketChannel = SocketChannel.open();
            //net地址
            InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 8000);
            //设置为非阻塞
            socketChannel.configureBlocking(false);
            socketChannel.connect(inetSocketAddress);
            while (!socketChannel.finishConnect()) {
                System.out.println("正在连接。。。。");
//                socketChannel.connect(inetSocketAddress);
            }

            String str = "hello, 服务器";
            ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
            socketChannel.write(buffer);
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
