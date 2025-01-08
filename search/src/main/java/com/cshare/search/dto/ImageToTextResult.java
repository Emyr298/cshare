package com.cshare.search.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ImageToTextResult {
    @JsonProperty("content_resource_id")
    private String contentResourceId;

    @JsonProperty("content_id")
    private String contentId;

    private String text;
}
