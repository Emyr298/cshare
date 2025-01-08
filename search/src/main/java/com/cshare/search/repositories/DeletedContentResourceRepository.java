package com.cshare.search.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import com.cshare.search.models.DeletedContentResource;

public interface DeletedContentResourceRepository extends ReactiveSortingRepository<DeletedContentResource, String>,
        ReactiveCrudRepository<DeletedContentResource, String> {

}
