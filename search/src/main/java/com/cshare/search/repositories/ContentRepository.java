package com.cshare.search.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import com.cshare.search.models.Content;
import com.cshare.search.repositories.customs.ContentLocking;

import reactor.core.publisher.Flux;

public interface ContentRepository
        extends ReactiveSortingRepository<Content, String>, ReactiveCrudRepository<Content, String>,
        ContentLocking {
    @Query("""
                {
                    "multi_match": {
                        "query": "?0",
                        "fields": ["resourceTexts.text", "title", "description"],
                        "fuzziness": "AUTO"
                    }
                }
            """)
    public Flux<Content> findByText(String text, Pageable pageable);
}
