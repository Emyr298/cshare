package com.cshare.search.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IngestContentDto {
    @NotNull
    private String id;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private String userId;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime updatedAt;

    private LocalDateTime publishedAt;
}
