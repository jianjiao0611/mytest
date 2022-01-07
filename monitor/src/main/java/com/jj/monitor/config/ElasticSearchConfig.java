package com.jj.monitor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * es 配置
 */
@Configuration
public class ElasticSearchConfig {

    /**
     * es ip
     */
    @Value("${es.ip}")
    private String ip;
    /**
     * es 端口
     */
    @Value("${es.port}")
    private String port;
    /**
     * es indexAlias
     */
    @Value("${es.indexAlias}")
    private String indexAlias;
}
