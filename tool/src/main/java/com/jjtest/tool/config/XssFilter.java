package com.jjtest.tool.config;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class XssFilter implements Filter {

//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        /*
         * //设置request字符编码 request.setCharacterEncoding("UTF-8");
         * //设置response字符编码 response.setContentType("text/wrapper;charset=UTF-8");
         */

        HttpServletRequest req = (HttpServletRequest) servletRequest;

        filterChain.doFilter(new XSSRequestWrapper(req), servletResponse);
    }

    @Override
    public void destroy() {

    }
}
