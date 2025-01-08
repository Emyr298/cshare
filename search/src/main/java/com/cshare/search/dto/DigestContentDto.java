package com.cshare.search.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.cshare.search.models.ContentResourceText;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DigestContentDto {
    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private String userId;

    @NotEmpty
    private List<ContentResourceText> resourceTexts;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    private LocalDateTime updatedAt;

    private LocalDateTime publishedAt;
}
