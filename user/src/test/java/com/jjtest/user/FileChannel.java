package com.jjtest.user;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileChannel {

    public static void main(String[] args) {
        try {
            File file = new File("C://test2.txt");

            FileInputStream inputStream = new FileInputStream(file);

            byte[] bytes = new byte[1024];

            File file1 = new File("C://text3.txt");
            FileOutputStream outputStream = new FileOutputStream(file1);
            int n=0;
            while ( (n = inputStream.read(bytes) )!= -1) {
                outputStream.write(bytes, 0 ,n);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
