package com.jjtest.zuul.config;

import com.jjtest.tool.constant.ResultConstant;
import com.jjtest.tool.context.LogDataThreadLocal;
import com.jjtest.tool.log.LogUtils;
import com.jjtest.tool.model.InterfaceLogPO;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

@Component
public class PostFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        InterfaceLogPO interfaceLog = LogDataThreadLocal.getInterfaceLog();
        if (interfaceLog != null) {
            interfaceLog.setEndTime(System.currentTimeMillis());
            interfaceLog.setCostTime(interfaceLog.getEndTime() - interfaceLog.getCallTime());

            LogUtils.interfaceLog("", ResultConstant.SUCCESS_CODE);
        }
        return true;
    }
}
