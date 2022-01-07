package com.jjtest.tool.model;

import lombok.Data;

/**
 * 日志对象
 */
@Data
public class InterfaceLogPO {

    private String id;

    private String traceKey;

    private String service;

    private String visitIp;

    private String url;

    private String method;

    private String time;

    private int orderNum;

    private String param;

    private String result;

    private String errorMsg;

    private Long callTime;

    private long costTime;

    private Long endTime;

    private String resultCode;
}
