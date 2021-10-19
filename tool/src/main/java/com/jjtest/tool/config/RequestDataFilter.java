package com.jjtest.tool.config;

import com.jjtest.tool.context.DefaultContextDataThreadLocal;
import com.jjtest.tool.model.LoginUserModel;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class RequestDataFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            LoginUserModel loginUser = (LoginUserModel) session.getAttribute("loginUser");
            DefaultContextDataThreadLocal.setLoginUser(loginUser);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
