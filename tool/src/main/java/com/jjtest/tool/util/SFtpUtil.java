package com.jjtest.tool.util;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jjtest.tool.exception.ServiceException;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.util.Properties;

/**
 * 文件上传工具类
 */
public class SFtpUtil {

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

    public static Session getSession(String account, String password, String ip, int port) {
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
            throw new ServiceException();
        } finally {
            if (session != null) {
                session.disconnect();
            }
        }
    }
}
