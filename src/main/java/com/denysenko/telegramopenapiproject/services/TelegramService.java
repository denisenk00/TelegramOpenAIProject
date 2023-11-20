package com.denysenko.telegramopenapiproject.services;

import com.denysenko.telegramopenapiproject.exceptions.TelegramApiException;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@Log4j
public class TelegramService extends DefaultAbsSender {

    @Value("${telegram.bot.token}")
    private String botToken;

    protected TelegramService() {
        super(new DefaultBotOptions());
    }

    public void sendMessage(Long chatId, String text){
        SendMessage.SendMessageBuilder messageBuilder = SendMessage.builder()
                .chatId(String.valueOf(chatId))
                .text(text.trim());
        try {
            execute(messageBuilder.build());
        } catch (org.telegram.telegrambots.meta.exceptions.TelegramApiException e) {
            log.error("Failed sending message to chatId = " + chatId, e);
            throw new TelegramApiException("Failed sending message to chatId = " + chatId, e);
        }
    }

    @Override
    public String getBotToken() {
        return botToken;
    }
}
