package com.jjtest.tool.util.sftp;

import lombok.Data;

/**
 * ftp站点信息
 */
@Data
public class FtpSitePo {
    /**
     * ip
     */
    private String ip;
    /**
     * 端口
     */
    private int port;
    /**
     * 账号
     */
    private String account;
    /**
     * 密码
     */
    private String password;
}
