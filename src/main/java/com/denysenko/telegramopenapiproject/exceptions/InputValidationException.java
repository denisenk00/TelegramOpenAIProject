package com.denysenko.telegramopenapiproject.exceptions;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public class InputValidationException extends RuntimeException implements ApiException {
    private String message;

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
