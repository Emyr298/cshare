package com.cshare.content.dto;

import lombok.Data;

import com.cshare.content.models.ContentStatus;

import jakarta.validation.constraints.NotNull;

@Data
public class CreateContentDto {
    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private ContentStatus status;
}
