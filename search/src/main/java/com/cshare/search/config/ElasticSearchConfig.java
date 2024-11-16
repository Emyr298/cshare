package com.cshare.search.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchConfiguration;

@Configuration
public class ElasticSearchConfig extends ReactiveElasticsearchConfiguration {
    @Value("${cshare.search.elastic-search.host}")
    private String elasticSearchHost;

    @Value("${cshare.search.elastic-search.user}")
    private String elasticSearchUser;

    @Value("${cshare.search.elastic-search.password}")
    private String elasticSearchPassword;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
            .connectedTo(elasticSearchHost)
            .withBasicAuth(elasticSearchUser, elasticSearchPassword)
            .build();
    }
}
