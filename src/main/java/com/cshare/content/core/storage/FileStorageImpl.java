package com.cshare.content.core.storage;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

@Component
@RequiredArgsConstructor
public class FileStorageImpl implements FileStorage {
    private final S3AsyncClient s3client;

    @Value("${aws.bucketname}")
    private String bucketName;

    public Mono<URL> uploadFile(String remotePath, File file) {
        PutObjectRequest putRequest = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(remotePath)
            .acl(ObjectCannedACL.PUBLIC_READ)
            .build();
        
        GetUrlRequest urlRequest = GetUrlRequest.builder()
            .bucket(bucketName)
            .key(remotePath)
            .build();

        Mono<PutObjectResponse> putResponse = Mono.fromFuture(
            s3client.putObject(
                putRequest,
                Path.of(file.toURI())
            )
        );

        Mono<URL> urlResponse =  Mono.just(s3client.utilities().getUrl(urlRequest));

        return putResponse.then(urlResponse);
    }
}
