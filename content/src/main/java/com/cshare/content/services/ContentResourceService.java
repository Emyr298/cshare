package com.cshare.content.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;

import com.cshare.content.core.storage.FileStorage;
import com.cshare.content.models.ContentResource;
import com.cshare.content.repositories.ContentResourceRepository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

@Service
@RequiredArgsConstructor
public class ContentResourceService {
    private final ContentResourceRepository repository;
    private final FileStorage fileStorage;

    @Value("${cshare.content.temp-file-path}")
    private String tempFilePath;

    @Value("${cshare.content.bucket-path}")
    private String bucketPath;

    private Mono<Path> saveTempFile(FilePart filePart) {
        String uuid = UUID.randomUUID().toString();
        Path path = Paths.get(tempFilePath, uuid);
        return filePart.transferTo(path).then(Mono.just(path));
    }

    public Mono<ContentResource> createResource(String userId, String contentId, Mono<FilePart> fileMono) {
        return fileMono
            .flatMap(filePart -> 
                saveTempFile(filePart)
                    .map(path -> Tuples.of(filePart, path)))
            .flatMap(pair -> {
                    return fileStorage.uploadFile(
                        pair.getT1().filename().replaceAll("[^A-Za-z0-9]", ""),
                        pair.getT2().toFile()
                    );
                }
            ).flatMap(url -> {
                ContentResource resource = ContentResource.builder()
                    .contentId(UUID.fromString(contentId))
                    .url(url.toString())
                    .build();
                return repository.save(resource);
            });
    }
}
