package com.jjtest.user;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOTest {


    public static void main(String[] args) throws Exception{


        ExecutorService executorService = Executors.newCachedThreadPool();

        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器已启动");
        while (true) {
            final Socket socket = serverSocket.accept();
            System.out.println("已连接客户端");
            executorService.execute(()->{
                handler(socket);
            });
        }
    }

    public static void handler(Socket socket) {

        try {
            System.out.println("线程：" + Thread.currentThread().getName());
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();

            while (true) {
                System.out.println("线程：" + Thread.currentThread().getName());
                int read = inputStream.read(bytes);
                if (read !=-1) {
                    System.out.println(new String(bytes, 0, read));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
