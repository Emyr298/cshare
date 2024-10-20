package com.cshare.search.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
@Document(indexName = "content")
public class Content {
    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String title;
    
    @Field(type = FieldType.Text)
    private String description;

    @Field(type = FieldType.Keyword)
    private String userId;

    @Field(type = FieldType.Date)
    private LocalDateTime createdAt;

    @Field(type = FieldType.Date)
    private LocalDateTime updatedAt;

    @Field(type = FieldType.Date)
    private LocalDateTime publishedAt;
}
