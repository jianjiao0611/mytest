package com.jjtest.tool.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean dataFilerRegistration() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(getRequestDataFilter());

        registrationBean.addUrlPatterns("/*");
        registrationBean.setName("requestDataFilter");
        registrationBean.setOrder(1);
        return registrationBean;
    }

    @Bean
    public RequestDataFilter getRequestDataFilter() {
        return new RequestDataFilter();
    }
}
