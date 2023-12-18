package com.denysenko.telegramopenapiproject.exceptions;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsAuthenticationException extends AuthenticationException{

    public UserAlreadyExistsAuthenticationException(String explanation) {
        super(explanation);
    }
    @Override
    public HttpStatus getStatus() {
        return HttpStatus.CONFLICT;
    }
}
