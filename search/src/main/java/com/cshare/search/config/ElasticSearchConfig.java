package com.cshare.search.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class ElasticSearchConfig extends ReactiveElasticsearchConfiguration {
    @Value("${cshare.search.elastic-search.host}")
    private String elasticSearchHost;

    @Value("${cshare.search.elastic-search.user}")
    private String elasticSearchUser;

    @Value("${cshare.search.elastic-search.password}")
    private String elasticSearchPassword;

    @Value("${cshare.search.elastic-search.certificate}")
    private String elasticSearchCertificate;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(elasticSearchHost)
                .usingSsl(elasticSearchCertificate)
                .withBasicAuth(elasticSearchUser, elasticSearchPassword)
                .build();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}
