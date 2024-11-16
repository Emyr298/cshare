package com.cshare.content.core.storage;

import java.io.File;
import java.net.URL;

import reactor.core.publisher.Mono;

public interface FileStorage {
    Mono<URL> uploadFile(String remotePath, File file);
}
