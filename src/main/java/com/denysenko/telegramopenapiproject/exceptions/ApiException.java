package com.denysenko.telegramopenapiproject.exceptions;

import org.springframework.http.HttpStatus;

public interface ApiException {
    String getMessage();
    HttpStatus getStatus();
}
