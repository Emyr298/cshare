package com.cshare.content.controller;

import java.time.LocalDateTime;

import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.cshare.content.dto.CreateContentDto;
import com.cshare.content.models.Content;
import com.cshare.content.models.ContentResource;
import com.cshare.content.services.ContentResourceService;
import com.cshare.content.services.ContentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/content")
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;
    private final ContentResourceService contentResourceService;
    
    @GetMapping("/users/{userId}")
    public Flux<Content> getContentByUser(
        @PathVariable String userId,
        @RequestParam(required = false) LocalDateTime fromTime,
        @RequestParam(required = false) LocalDateTime toTime
    ) {
        return contentService.findContentByUser(userId, fromTime, toTime);
    }

    @GetMapping("/drafts")
    public Flux<Content> getDrafts(
        @RequestParam String userId
    ) {
        return contentService.findDrafts(userId);
    }

    @GetMapping("/{contentId}")
    public Mono<Content> getContent(
        @PathVariable String contentId,
        @RequestParam String userId
    ) {
        return contentService.getContent(userId, contentId);
    }

    @PostMapping("/")
    public Mono<Content> createContent(
        @Valid @RequestBody CreateContentDto payload,
        @RequestParam String userId
    ) {
        return contentService.createContent(userId, payload);
    }

    @PostMapping("/{contentId}/resources")
    public Mono<ContentResource> uploadResource(
        @RequestParam String userId,
        @PathVariable String contentId,
        @RequestPart("image") Mono<FilePart> file
    ) {
        return contentResourceService.createResource(userId, contentId, file);
    }
}
