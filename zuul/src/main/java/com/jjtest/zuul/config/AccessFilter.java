package com.jjtest.zuul.config;

import com.jjtest.tool.constant.ResultConstant;
import com.jjtest.tool.context.LogDataThreadLocal;
import com.jjtest.tool.log.LogUtils;
import com.jjtest.tool.model.InterfaceLogPO;
import com.jjtest.tool.util.DateUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;

@Component
public class AccessFilter extends ZuulFilter {

    @Autowired
    private HttpServletRequest request;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpSession session = request.getSession(false);
        String logId = null;
        String sessionId = null;
        if (session != null) {
            sessionId = session.getId();
            logId = (String) session.getAttribute("logId");
        }

        String uri = request.getRequestURI();
        String token = request.getHeader("token");
        String traceKey = UUID.randomUUID().toString().replaceAll("-", "");
        ctx.addZuulRequestHeader("log_num", "1");
        ctx.addZuulRequestHeader("log_traceKey", traceKey);
        setLog(traceKey);
        return successResponse(ctx, "error");
    }

    private String errorResponse(RequestContext ctx, String msg) {
        // 对该请求禁止路由，禁止访问下游服务
        ctx.setSendZuulResponse(false);
        ctx.setResponseBody(msg);
        ctx.setResponseStatusCode(200);
        return "false";
    }

    private String successResponse(RequestContext ctx, String sessionId) {
        ctx.setSendZuulResponse(true);
        ctx.setResponseStatusCode(200);
        return "true";
    }

    private void setLog(String traceKey) {
        //接口日志
        InterfaceLogPO logPO = new InterfaceLogPO();
        logPO.setTraceKey(traceKey);
        logPO.setId(UUID.randomUUID().toString());
        logPO.setMethod(request.getMethod());
        logPO.setUrl(request.getRequestURI());
        logPO.setTime(DateUtils.dateTimeNow());
        logPO.setVisitIp(request.getRemoteAddr());
        logPO.setOrderNum(1);
        logPO.setCallTime(System.currentTimeMillis());

        LogDataThreadLocal.setInterfaceLog(logPO);
        LogUtils.interfaceLog("", ResultConstant.SUCCESS_CODE);
    }
}
