package com.jjtest.tool.config;

import com.jjtest.tool.constant.ResultConstant;
import com.jjtest.tool.context.DefaultContextDataThreadLocal;
import com.jjtest.tool.context.LogDataThreadLocal;
import com.jjtest.tool.log.LogUtils;
import com.jjtest.tool.model.InterfaceLogPO;
import com.jjtest.tool.model.LoginUserModel;
import com.jjtest.tool.response.ResultObject;
import com.jjtest.tool.util.DateUtils;
import com.jjtest.tool.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

public class RequestDataFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            LoginUserModel loginUser = (LoginUserModel) session.getAttribute("loginUser");
            DefaultContextDataThreadLocal.setLoginUser(loginUser);
        }
        Integer logNum = StringUtils.getInteger(httpServletRequest.getHeader("log_num"), 0);
        String traceKey = httpServletRequest.getHeader("log_traceKey");
        //接口日志
        InterfaceLogPO logPO = new InterfaceLogPO();
        logPO.setTraceKey(traceKey);
        logPO.setId(UUID.randomUUID().toString());
        logPO.setMethod(httpServletRequest.getMethod());
        logPO.setUrl(httpServletRequest.getRequestURI());
        logPO.setTime(DateUtils.dateTimeNow());
        logPO.setVisitIp(httpServletRequest.getRemoteAddr());
        logPO.setOrderNum(logNum + 1);
        logPO.setCallTime(System.currentTimeMillis());

        LogDataThreadLocal.setInterfaceLog(logPO);
        filterChain.doFilter(servletRequest, servletResponse);

        InterfaceLogPO interfaceLog = LogDataThreadLocal.getInterfaceLog();
        if (interfaceLog != null) {
            interfaceLog.setEndTime(System.currentTimeMillis());
            interfaceLog.setCostTime(interfaceLog.getEndTime() - interfaceLog.getCallTime());
        }
    }

    @Override
    public void destroy() {

    }
}
