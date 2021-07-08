package com.jjtest.tool.util.sftp;

import lombok.Data;

@Data
public class FtpSitePo {

    private String ip;
    private int port;
    private String account;

    private String password;
}
