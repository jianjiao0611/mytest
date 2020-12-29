package com.jjtest.taskcenter.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: java类作用描述
 * @Author: jianjiao
 * @CreateDate: 2019/9/28$ 11:57$
 * @UpdateUser:
 * @UpdateDate: 2019/9/28$ 11:57$
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
@Configuration
public class FeignLogConfig {

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
