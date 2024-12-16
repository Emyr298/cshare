package com.cshare.user.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ErrorDto {
    private LocalDateTime timestamp;
    private String path;
    private Integer status;
    private String error;
    private String requestId;
    private String message;
}
