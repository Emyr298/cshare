package com.cshare.search.repositories.customs;

import org.springframework.data.elasticsearch.core.query.SeqNoPrimaryTerm;

import com.cshare.search.models.Content;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;

@AllArgsConstructor
public class ContentLockingImpl implements ContentLocking {
    private final ObjectMapper objectMapper;
    private final ReactiveLockingHelper helper;
    private static final String INDEX_NAME = "content";

    public Mono<Content> update(Content obj, SeqNoPrimaryTerm seqNoPrimaryTerm) {
        return helper.update(INDEX_NAME, obj, seqNoPrimaryTerm);
    }

    public Mono<Tuple2<Content, SeqNoPrimaryTerm>> findByIdWithSeqNoPrimaryTerm(String id) {
        return helper.findByIdWithSeqNoPrimaryTerm(INDEX_NAME, id, ObjectNode.class).flatMap(result -> {
            Mono<Content> decodedContent = Mono.defer(() -> Mono.fromCallable(() -> {
                try {
                    return objectMapper.treeToValue(result.getT1(), Content.class);
                } catch (Exception e) {
                    throw new IllegalArgumentException("Invalid database format");
                }
            }).subscribeOn(Schedulers.boundedElastic()));
            return decodedContent.flatMap(content -> Mono.zip(Mono.just(content), Mono.just(result.getT2())));
        });
    }
}
