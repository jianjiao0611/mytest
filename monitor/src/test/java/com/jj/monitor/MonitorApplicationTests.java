package com.jj.monitor;

import com.jj.monitor.config.ElasticSearchConfig;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.TransportRequestOptions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MonitorApplicationTests {

    @Autowired
    private ElasticSearchConfig elasticSearchConfig;

    @Test
    public void contextLoads() {

        try {
            RestHighLevelClient client = elasticSearchConfig.getRestHighLevelClient();

            SearchRequest searchRequest = new SearchRequest(elasticSearchConfig.getIndexAlias());
            BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
            queryBuilder.must(QueryBuilders.matchQuery("traceKey","8c05ff715e394b2a97d27b4d0c797023"));
            queryBuilder.must(QueryBuilders.matchQuery("service","user"));
            SearchSourceBuilder builder = new SearchSourceBuilder().query(queryBuilder);
            builder.sort("orderNum", SortOrder.ASC);
            String[] includes ={"service","callTime","endTime","costTime","orderNum","result","traceKey","url"};
            String[] excludes = {};
            builder.fetchSource(includes, excludes);
            searchRequest.source(builder);


            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);

            SearchHits hits = response.getHits();
            System.out.println(hits.getTotalHits());
            System.out.println(hits.getTotalHits());

            for (SearchHit hit : hits) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                System.out.println(sourceAsMap);
            }

            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
