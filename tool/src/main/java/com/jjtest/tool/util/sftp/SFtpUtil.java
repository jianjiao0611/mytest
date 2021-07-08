package com.jjtest.tool.util.sftp;

import com.jcraft.jsch.*;
import com.jjtest.tool.exception.ServiceException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Vector;

/**
 * 文件上传工具类
 */
public class SFtpUtil {

    private static Logger logger = LoggerFactory.getLogger(SFtpUtil.class);

    public static String md5Sum(Session session, String srcFolder, String fileName) {
        ChannelExec channelExec = null;
        InputStream in = null;
        try {
            channelExec = (ChannelExec) session.openChannel("exec");
            String cmd = "md5sum" + srcFolder + "/" + fileName;
            channelExec.setCommand(cmd);
            in = channelExec.getInputStream();
            channelExec.connect(2000);
            String resultMd5 = IOUtils.toString(in, "UTF-8");
            return resultMd5.split("\\s+")[0];
        } catch (Exception e) {
            throw new ServiceException();
        } finally {
            IOUtils.closeQuietly(in);
            channelExec.disconnect();
        }
    }

    private static Session getSession(String account, String password, String ip, int port) {
        Session session = null;
        try {
            session = new JSch().getSession(account, ip, port);
            if (session == null) {
                throw new ServiceException();
            }
            Properties properties = new Properties();
            properties.put("StrictHostKeyChecking", "no");
            session.setConfig(properties);
            session.setPassword(password);
            session.connect(2000);
            return session;
        } catch (Exception e) {
            if (session != null) {
                session.disconnect();
            }
            throw new ServiceException();
        }
    }

    public static SftpChannelInfo getChannelSftp(FtpSitePo po) {
        SftpChannelInfo channelInfo = new SftpChannelInfo();
        ChannelSftp channelSftp = null;
        Session session = null;
        try {
            session = getSession(po.getAccount(), po.getPassword(), po.getIp(), po.getPort());
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect(2000);
        } catch (JSchException e) {
            if (channelSftp != null) {
                channelSftp.disconnect();
            }
        }
        channelInfo.setChannelSftp(channelSftp);
        channelInfo.setSession(session);
        return channelInfo;
    }

    public static void uploadFile(SftpChannelInfo sftpChannelInfo, String folder, String fileName, InputStream inputStream) {
        OutputStream outputStream = null;
        ChannelSftp channelSftp = sftpChannelInfo.getChannelSftp();
        try {
            //创建文件目录
            mkdir(folder, channelSftp);
            //进入文件夹
            channelSftp.cd(folder);
            outputStream = channelSftp.put(fileName);
            byte[] bf = new byte[1024*256];
            int n;
            while ((n = inputStream.read(bf)) != -1) {
                outputStream.write(bf, 0, n);
            }
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }
    }

    public static void mkdir(String dir, ChannelSftp channelSftp) {
        try {
            //进入根目录，否则进入sftp用户home目录
            channelSftp.cd("/");
            String[] folders = dir.split("/");
            for (int i = 1; i < folders.length; i++) {
                if (StringUtils.isBlank(folders[i])) {
                    throw new ServiceException("文件路径错误");
                }
                if (!checkDirExist(folders[i], channelSftp)) {
                    channelSftp.mkdir(folders[i]);
                }
                channelSftp.cd(folders[i]);
            }
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkDirExist(String dir, ChannelSftp channelSftp) {
        try {
            Vector vector = channelSftp.ls(dir);
            if (vector == null) {
                return false;
            }
        } catch (SftpException e) {
            logger.error("未找到文件{}", dir);
            return false;
        }
        return true;
    }

    public static void change(String dir, String dd, SftpChannelInfo sftpChannelInfo) {
        ChannelSftp channelSftp = sftpChannelInfo.getChannelSftp();
        try {
            channelSftp.cd(dir);

            String home = channelSftp.getHome();
            System.out.println(home);
        } catch (SftpException e) {
            e.printStackTrace();
        }

    }
}
