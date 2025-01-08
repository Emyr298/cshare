package com.cshare.search.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cshare.search.dto.DigestContentDto;
import com.cshare.search.models.Content;
import com.cshare.search.services.SearchService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/")
    public Flux<Content> search(
            @RequestParam String query,
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize) {
        return searchService.search(query, pageNumber, pageSize);
    }

    @PostMapping("/digest")
    public Mono<Content> digest(
            @Valid @RequestBody DigestContentDto payload) {
        return searchService.digest(payload);
    }
}
