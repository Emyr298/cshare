package com.cshare.content.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Content {
    @Id
    @Column("id")
    private UUID id;
    
    @Column("title")
    private String title;
    
    @Column("description")
    private String description;
    
    @Column("user_id")
    private UUID userId;
    
    @Column("status")
    private ContentStatus status;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Column("published_at")
    private LocalDateTime publishedAt;
}
