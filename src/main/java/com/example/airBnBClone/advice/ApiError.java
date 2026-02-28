package com.example.airBnBClone.advice;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Represents an error response for API requests.
 */

@Data
@Builder
public class ApiError {

    private int status;
    private String message;
    private List<String> subErrors;


}
