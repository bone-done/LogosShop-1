package com.vansisto.logosshop.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    private Integer status;
    private String message;
    private String path;
    private Map<String, String> validationErrors;

    public ApiError(Integer status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
    }
}
