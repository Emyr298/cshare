package com.cshare.user.core.storage.adapters;

import java.net.URL;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;

import com.cshare.user.core.storage.FileStorage;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

@Component
@RequiredArgsConstructor
public class FilePartStorageImpl implements FilePartStorage {
    private final FileStorage fileStorage;

    @Value("${cshare.user.temp-file-path}")
    private String tempFilePath;

    private Mono<Path> saveTempFile(FilePart filePart) {
        String uuid = UUID.randomUUID().toString();
        Path path = Paths.get(tempFilePath, uuid);
        return filePart.transferTo(path).then(Mono.just(path));
    }

    public Mono<URL> uploadFile(FilePart filePart) {
        return Mono.just(filePart)
                .flatMap(curFilePart -> saveTempFile(curFilePart)
                        .map(path -> Tuples.of(curFilePart, path)))
                .flatMap(pair -> fileStorage.uploadFile(
                        pair.getT1().filename().replaceAll("[^A-Za-z0-9]", "_"),
                        pair.getT2().toFile()));
    }
}
