package org.hrd.homeworkspringdatajpa.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private String message;
    private T payload;
    private String  status;
    private final LocalDateTime instant = LocalDateTime.now();
}