package com.cshare.content.config;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;

@Configuration
public class S3ClientConfig {
    @Value("${aws.endpoint}")
    private String awsEndpoint;

    @Value("${aws.region}")
    private String awsRegion;

    @Bean
    public S3AsyncClient s3AsyncClient() {
        return S3AsyncClient.builder()
            .region(Region.of(awsRegion))
            .endpointOverride(URI.create(awsEndpoint))
            .forcePathStyle(true)
            .build();
    }
}
