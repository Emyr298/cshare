package com.cshare.search.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import com.cshare.search.models.Content;

import reactor.core.publisher.Flux;

public interface ContentRepository
        extends ReactiveSortingRepository<Content, String>, ReactiveCrudRepository<Content, String> {
    @Query("""
        {
            "bool": {
                "should": [
                    {
                        "fuzzy": {
                            "title": "?0"
                        }
                    },
                    {
                        "fuzzy": {
                            "description": "?0"
                        }
                    }
                ]
            }
        }
    """)
    public Flux<Content> findByTitleOrDescription(String title, String description, Pageable pageable);
}
