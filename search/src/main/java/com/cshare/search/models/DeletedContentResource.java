package com.cshare.search.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Document(indexName = "content_resource_deleted")
public class DeletedContentResource {
    @Id
    private String id;
}
