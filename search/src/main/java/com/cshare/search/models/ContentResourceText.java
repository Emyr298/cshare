package com.cshare.search.models;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ContentResourceText {
    @NotNull
    private String id;

    @NotNull
    private String contentId;

    @NotNull
    private String text;
}
