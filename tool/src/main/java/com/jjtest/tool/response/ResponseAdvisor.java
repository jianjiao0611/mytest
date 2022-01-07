package com.jjtest.tool.response;

import com.alibaba.fastjson.JSONObject;
import com.jjtest.tool.context.DefaultContextDataThreadLocal;
import com.jjtest.tool.context.LogDataThreadLocal;
import com.jjtest.tool.log.LogUtils;
import com.jjtest.tool.model.InterfaceLogPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * ResponseBodyAdvice接口是在Controller执行return之后，在response返回给浏览器或者APP客户端之前，
 * 执行的对response的一些处理。可以实现对response数据的一些统一封装或者加密等操作
 */
@RestControllerAdvice
public class ResponseAdvisor implements ResponseBodyAdvice<Object> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseAdvisor.class);

    /**
     *  判断是否要执行beforeBodyWrite方法，true为执行，false不执行
     * @param methodParameter
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {

        try {
            InterfaceLogPO interfaceLog = LogDataThreadLocal.getInterfaceLog();
            if (interfaceLog == null) {
                return body;
            }
            if (body instanceof ResultObject) {
                ResultObject resultObject = (ResultObject) body;
                interfaceLog.setResultCode(resultObject.getCode());
                interfaceLog.setResult(JSONObject.toJSONString(resultObject));
//                if (ResultConstant.SUCCESS_CODE.equals(resultObject.getCode())) {
//
//                }
            } else {
                interfaceLog.setResult(JSONObject.toJSONString(body));
            }
            LogUtils.interfaceLog(interfaceLog);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            DefaultContextDataThreadLocal.cleanLoginUser();
            LogDataThreadLocal.removeLog();
        }
        return body;
    }
}
