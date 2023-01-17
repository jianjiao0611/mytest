//package com.jjtest.tool.config;
//
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.servlet.Filter;
//
///**
// * 防xss攻击filter
// */
//@Configuration
//public class XssFilterRegister {
//
////    @Bean(name = "xssFilterRegistrationBean")
////    public FilterRegistrationBean dataFilerRegistration() {
////        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
////        registrationBean.setFilter(xssFilter());
////
////        registrationBean.addUrlPatterns("/*");
////        registrationBean.setName("xssFilter");
////        registrationBean.setOrder(Integer.MAX_VALUE);
////        return registrationBean;
////    }
//
////    /**
////     * 创建一个bean
////     *
////     * @return
////     */
////    @Bean
////    public Filter xssFilter() {
////        return new XssFilter();
////    }
//
//}
