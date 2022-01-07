package com.jjtest.tool.response;

import com.jjtest.tool.exception.ServiceException;
import com.jjtest.tool.response.ResultObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常
 */
@Slf4j
@ControllerAdvice
public class TopException {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultObject handle(Exception e) {
        ResultObject resultObject = new ResultObject();
        if (e instanceof ServiceException) {
            log.error("业务日志", e);
            ServiceException serviceException = (ServiceException) e;
            resultObject.resultObject(null, serviceException.getCode() + "", serviceException.getMsg());
            return  resultObject;
        }
        log.error("系统日志", e);
        resultObject.errorResultObject();
        return resultObject;
    }

}
