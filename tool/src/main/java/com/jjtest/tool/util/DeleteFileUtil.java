package com.jjtest.tool.util;

import java.io.File;

public class DeleteFileUtil {

    public static boolean delTmpFolder(String path) {
        //删除文件夹下所有文件
        delAllFolderFiles(path);
        File file = new File(path);
        if (!file.delete()) {
            return false;
        }
        return true;
    }

    public static void delAllFolderFiles(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        String[] list = file.list();
        if (list == null) {
            return;
        }
        File tmpFile = null;
        for (int i = 0; i < list.length; i++) {
            tmpFile = new File(path + File.separator + list[i]);
            if (tmpFile.isFile()) {
                tmpFile.delete();
            }
        }
    }
}
