package com.cshare.search.services;

import org.springframework.stereotype.Service;

import com.cshare.search.dto.ImageToTextResult;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.kafka.receiver.ReceiverRecord;

@Service
@RequiredArgsConstructor
public class ImageToTextServiceImpl implements ImageToTextService {
    private final Flux<ReceiverRecord<String, ImageToTextResult>> receiver;
    private final SearchService searchService;

    @PostConstruct
    public void startReceiver() {
        receiver
                .concatMap(message -> {
                    return processMessage(message.value())
                            .then(message.receiverOffset().commit());
                })
                .retry()
                .subscribe();
    }

    private Mono<Void> processMessage(ImageToTextResult message) {
        return searchService.ingestImageToText(message);
    }
}
