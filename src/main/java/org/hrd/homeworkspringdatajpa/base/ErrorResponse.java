package org.hrd.homeworkspringdatajpa.base;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    private String message;
    private int status;
    private final LocalDateTime timestamp = LocalDateTime.now();
}
