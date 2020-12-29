package com.jjtest.tool.response;

import lombok.Data;

/**
 * @Description
 * @Param
 * @Return
 * @Author
 * @Date 2019/4/24 15:17
 */
@Data
public class ResultObject<T> {

    private T data;

    private int code;

    private String msg;

    public void resultObject(T data,int code,String msg){
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public void successResultObject(T data){
        this.data = data;
        this.code = 200;
        this.msg = "操作成功";
    }

    public void successResultObject(){
        this.data = data;
        this.code = 200;
        this.msg = "操作成功";
    }

    public void errorResultObject(T data){
        this.data = data;
        this.code = 400;
        this.msg = "操作失败";
    }

    public void errorResultObject(){
        this.data = data;
        this.code = 400;
        this.msg = "操作失败";
    }
}
