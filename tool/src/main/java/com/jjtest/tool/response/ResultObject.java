package com.jjtest.tool.response;

import com.jjtest.tool.constant.ResultConstant;
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

    private String code;

    private String msg;

    public ResultObject() {
    }

    public ResultObject(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public void resultObject(T data, String code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public void successResultObject(T data) {
        this.data = data;
        this.code = ResultConstant.SUCCESS_CODE;
        this.msg = "操作成功";
    }

    public void successResultObject() {
        this.code = ResultConstant.SUCCESS_CODE;
        this.msg = "操作成功";
    }

    public void errorResultObject(T data) {
        this.data = data;
        this.code = ResultConstant.ERROR_CODE;
        this.msg = "操作失败";
    }

    public void errorResultObject() {
        this.code = ResultConstant.ERROR_CODE;
        this.msg = "操作失败";
    }

    public static ResultObject successResult() {
        return new ResultObject(ResultConstant.SUCCESS_CODE, "操作成功");
    }

    public static ResultObject errorResult() {
        return new ResultObject(ResultConstant.ERROR_CODE, "操作失败");
    }
}
