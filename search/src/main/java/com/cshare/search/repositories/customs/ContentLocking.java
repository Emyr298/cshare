package com.cshare.search.repositories.customs;

import org.springframework.data.elasticsearch.core.query.SeqNoPrimaryTerm;

import com.cshare.search.models.Content;

import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

public interface ContentLocking {
    Mono<Content> update(Content obj, SeqNoPrimaryTerm seqNoPrimaryTerm);

    Mono<Tuple2<Content, SeqNoPrimaryTerm>> findByIdWithSeqNoPrimaryTerm(String id);
}
