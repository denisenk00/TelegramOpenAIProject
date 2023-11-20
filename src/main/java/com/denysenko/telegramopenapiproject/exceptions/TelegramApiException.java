package com.denysenko.telegramopenapiproject.exceptions;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TelegramApiException extends RuntimeException {

    private String message;
    private org.telegram.telegrambots.meta.exceptions.TelegramApiException ex;

}

