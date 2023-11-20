package com.denysenko.telegramopenapiproject.exceptions;

import org.springframework.http.HttpStatus;

import javax.naming.AuthenticationException;

public class AuthorizationException extends AuthenticationException implements ApiException{

    private Exception cause;

    public AuthorizationException(String explanation, Exception cause) {
        super(explanation);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.UNAUTHORIZED;
    }
}
