package com.restful.api.handler;

import org.springframework.validation.FieldError;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

public record ErrorDetails(
        LocalDateTime timestamp,
        String field,
        String details,
        String error
) {
    public ErrorDetails(FieldError erro) {
        this(
                now(),
                erro.getField(),
                erro.getDefaultMessage(),
                "BAD_REQUEST: " + erro.getCode()
        );
    }
}