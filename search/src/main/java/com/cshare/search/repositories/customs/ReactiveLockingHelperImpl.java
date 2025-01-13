package com.cshare.search.repositories.customs;

import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchClient;
import org.springframework.data.elasticsearch.core.query.SeqNoPrimaryTerm;
import org.springframework.stereotype.Component;

import com.cshare.search.utils.Identifiable;

import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Component
public class ReactiveLockingHelperImpl implements ReactiveLockingHelper {
    private final ReactiveElasticsearchClient client;

    public ReactiveLockingHelperImpl(ReactiveElasticsearchClient client) {
        this.client = client;
    }

    public <T> Mono<Tuple2<T, SeqNoPrimaryTerm>> findByIdWithSeqNoPrimaryTerm(
            String indexName, String id, Class<T> clazz) {
        Mono<GetResponse<T>> response = client.get(g -> g
                .index(indexName)
                .id(id), clazz);

        return response.flatMap(res -> Mono.zip(Mono.just(res.source()),
                Mono.just(new SeqNoPrimaryTerm(res.seqNo(), res.primaryTerm()))));
    }

    public <T extends Identifiable<String>> Mono<T> update(String indexName, T obj, SeqNoPrimaryTerm seqNoPrimaryTerm) {
        if (seqNoPrimaryTerm == null) {
            throw new IllegalArgumentException("seqNoPrimaryTerm cannot be null");
        }

        IndexRequest<Object> request = IndexRequest.of(i -> i
                .index(indexName)
                .id(obj.getId())
                .document(obj)
                .ifSeqNo(seqNoPrimaryTerm.sequenceNumber())
                .ifPrimaryTerm(seqNoPrimaryTerm.primaryTerm()));

        return client.index(request).then(Mono.just(obj));
    }
}
