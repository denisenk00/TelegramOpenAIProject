package com.denysenko.telegramopenapiproject.exceptions;

public class TelegramBotException extends RuntimeException{

    public TelegramBotException(String message, Exception e) {
        super(message, e);
    }
}
