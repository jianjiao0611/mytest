package com.jjtest.tool.exception;

import lombok.Data;

/**
 * 业务异常类
 */
@Data
public class ServiceException extends RuntimeException{

    private int code;

    private String msg;

    public ServiceException() {
        super();
    }

    public ServiceException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ServiceException(String msg) {
        this.msg = msg;
        this.code = 400;
    }

}
