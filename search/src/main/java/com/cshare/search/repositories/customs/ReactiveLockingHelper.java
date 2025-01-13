package com.cshare.search.repositories.customs;

import org.springframework.data.elasticsearch.core.query.SeqNoPrimaryTerm;

import com.cshare.search.utils.Identifiable;

import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

public interface ReactiveLockingHelper {
    <T> Mono<Tuple2<T, SeqNoPrimaryTerm>> findByIdWithSeqNoPrimaryTerm(
            String indexName, String id, Class<T> clazz);

    <T extends Identifiable<String>> Mono<T> update(String indexName, T obj, SeqNoPrimaryTerm seqNoPrimaryTerm);
}
