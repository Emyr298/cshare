package com.cshare.user.core.storage.adapters;

import java.net.URL;

import org.springframework.http.codec.multipart.FilePart;

import reactor.core.publisher.Mono;

public interface FilePartStorage {
    Mono<URL> uploadFile(FilePart filePart);
}
