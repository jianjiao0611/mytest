package com.jjtest.tool.util.sftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import lombok.Data;

/**
 * sftp连接信息
 */
@Data
public class SftpChannelInfo {

    private ChannelSftp channelSftp;

    private Session session;
}
