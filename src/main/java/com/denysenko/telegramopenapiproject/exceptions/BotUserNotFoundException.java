package com.denysenko.telegramopenapiproject.exceptions;

public class BotUserNotFoundException extends RuntimeException{

    private Long chatId;

    public BotUserNotFoundException(String message) {
        super(message);
    }

    public BotUserNotFoundException(Long chatId, String message){
        super(message);
        this.chatId = chatId;
    }
}
