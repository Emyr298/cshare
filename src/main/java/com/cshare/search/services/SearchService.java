package com.cshare.search.services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class SearchService {
    public Flux<String> search(String test) {
        return null;
    }
}
