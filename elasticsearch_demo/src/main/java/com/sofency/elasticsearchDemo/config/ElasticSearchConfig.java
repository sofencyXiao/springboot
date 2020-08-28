package com.sofency.elasticsearchDemo.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

/**
 * @author sofency
 * @date 2020/4/25 1:01
 * @package IntelliJ IDEA
 * @description
 */
@Configuration
public class ElasticSearchConfig {

    @Value("${elasticsearch.url}")
    private String url;
    @Bean
    public RestHighLevelClient restHighLevelClient(){
//        RestHighLevelClient client = new RestHighLevelClient(
//                RestClient.builder(
//                        new HttpHost("127.0.0.1",9200,"http")
//                )
//        );
//        return client;
        ClientConfiguration client = ClientConfiguration.builder().connectedTo(url).build();
        return RestClients.create(client).rest();
    }
}
