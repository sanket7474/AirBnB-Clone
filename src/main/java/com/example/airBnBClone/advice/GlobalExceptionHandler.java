package com.example.airBnBClone.advice;

import com.example.airBnBClone.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * A global exception handler that catches specific exceptions and returns a standardized API response.
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse<?>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message(ex.getMessage())
                .build();

        APIResponse<?> response = new APIResponse<>(apiError);
        return buildErrorResponse(apiError);
    }

    private ResponseEntity<APIResponse<?>> buildErrorResponse(ApiError apiError) {
        return ResponseEntity.status(apiError.getStatus()).body(new APIResponse<>(apiError));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<APIResponse<?>> handleGenericException(Exception ex) {
        ApiError apiError = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message("An unexpected error occurred")
                .build();

        return buildErrorResponse(apiError);
    }
}
