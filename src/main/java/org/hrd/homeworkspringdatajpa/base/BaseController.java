package org.hrd.homeworkspringdatajpa.base;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {

    // This is for no payload
    protected ResponseEntity<ApiResponse<Object>> response(String message) {
        return ResponseEntity.ok(ApiResponse.builder()
                .status(HttpStatus.OK.name()) // Use status name (e.g., "OK")
                .message(message)
                .build());
    }

    // This is for HTTP OK, mostly used for GET
    protected <T> ResponseEntity<ApiResponse<T>> response(String message, T payload) {
        ApiResponse<T> apiResponse = ApiResponse.<T>builder()
                .status(HttpStatus.OK.name()) // Use status name (e.g., "OK")
                .message(message)
                .payload(payload)
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    // This is for creation
    protected <T> ResponseEntity<ApiResponse<T>> response(String message, HttpStatus httpStatus, T payload) {
        ApiResponse<T> apiResponse = ApiResponse.<T>builder()
                .status(httpStatus.name()) // Use status name (e.g., "CREATED")
                .message(message)
                .payload(payload)
                .build();
        return ResponseEntity.status(httpStatus).body(apiResponse); // Use httpStatus for response
    }

}