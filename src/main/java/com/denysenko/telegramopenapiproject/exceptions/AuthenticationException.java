package com.denysenko.telegramopenapiproject.exceptions;

import org.springframework.http.HttpStatus;


public class AuthenticationException extends RuntimeException implements ApiException{

    private Exception cause;

    public AuthenticationException(String explanation, Exception cause) {
        super(explanation);
    }

    public AuthenticationException(String explanation) {
        super(explanation);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
