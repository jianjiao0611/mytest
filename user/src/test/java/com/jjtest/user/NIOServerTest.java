package com.jjtest.user;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * 1、创建ServerSocketChannel
 * 2、绑定端口
 * 3、创建selector
 *
 */
public class NIOServerTest {

    public static void main(String[] args) {
        try {
            //创建serverSocketChanel, 绑定端口
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            InetSocketAddress socketAddress = new InetSocketAddress(8000);
            serverSocketChannel.bind(socketAddress);
            //设置为非阻塞
            serverSocketChannel.configureBlocking(false);

            Selector selector = Selector.open();
            //将serverSocketChannel注册到Selector
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            //开始监听
            while (true) {
                //没有关注的事件发生
                if(selector.select(1000) == 0) {
                    System.out.println("没有关注的时间发生");
                    continue;
                }
                //获取到有事件发生的 keys
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    // 连接事件，有客户端连接
                    if(key.isAcceptable()){
                        //获取socketChannel
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        System.out.println("有客户端连接：" + socketChannel.hashCode());
                        //设置为非阻塞
                        socketChannel.configureBlocking(false);
                        //将socketChannel注册到 selector 注册时将buffer放入selectionKey,之后可通过key反向获取buffer
                        socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    }
                    // 读取事件
                    if(key.isReadable()) {
                        //通过key反向获取socketChannel
                        SocketChannel channel = (SocketChannel)key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        //将channel数据读取到buffer
                        channel.read(buffer);

                        System.out.println("from 客户端：" + new String(buffer.array()));
                    }
                    //删除key
                    iterator.remove();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
