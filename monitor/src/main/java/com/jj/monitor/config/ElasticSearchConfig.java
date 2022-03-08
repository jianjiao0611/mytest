package com.jj.monitor.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

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

//    private static TransportClient transportClient = null;

    private static RestHighLevelClient restHighLevelClient = null;

    @PostConstruct
    public void init() {
        try {
//            transportClient = TransportClient.builder().build()
//                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(this.ip), Integer.parseInt(this.port)));

            restHighLevelClient = new RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200, "http")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    public TransportClient getTransportClient() {
//        return transportClient;
//    }

    public RestHighLevelClient getRestHighLevelClient() {
        return restHighLevelClient;
    }

    public String getIndexAlias() {
        return indexAlias;
    }
}
