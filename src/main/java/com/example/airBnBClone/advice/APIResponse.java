package com.example.airBnBClone.advice;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * A generic API response wrapper that includes a timestamp, data, and error information.
 *
 * @param <T> the type of the data being returned in the response
 */

@Data
public class APIResponse<T> {

    private LocalDateTime timestamp;
    private T data;
    private ApiError error;

    public APIResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public APIResponse(T data) {
        this();
        this.data = data;
    }

    public APIResponse(ApiError error) {
        this();
        this.error = error;
    }
}
