package com.jjtest.tool.config;

import com.jjtest.tool.log.LogUtils;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Feign配置
 * 使用FeignClient进行服务间调用，传递headers信息
 */
@Configuration
public class FeignConfig implements RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //添加token
        System.out.println("----------------token--------------" + request.getHeader("token"));
        requestTemplate.header("token", request.getHeader("token"));
        //添加日志信息
        requestTemplate.header("log_num", LogUtils.getOrderNum() + "");
        requestTemplate.header("log_traceKey", request.getHeader("log_traceKey"));
    }
}
