package com.cshare.search.services;

import com.cshare.search.dto.DigestContentDto;
import com.cshare.search.dto.ImageToTextResult;
import com.cshare.search.models.Content;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SearchService {
    Flux<Content> search(String query, Integer pageNumber, Integer pageSize);

    Mono<Content> digest(DigestContentDto data);

    Mono<Void> ingestImageToText(ImageToTextResult result);
}
