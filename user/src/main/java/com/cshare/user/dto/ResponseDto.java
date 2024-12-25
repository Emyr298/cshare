package com.cshare.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResponseDto<T> {
    private String message;
    private T data;

    public ResponseDto(T data) {
        this.message = "Success";
        this.data = data;
    }
}
