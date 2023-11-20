package com.denysenko.telegramopenapiproject.configs;

import com.denysenko.telegramopenapiproject.tghandlers.GlobalUpdateHandler;
import jakarta.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Log4j
@Configuration
@RequiredArgsConstructor
public class TelegramBotConfiguration extends TelegramLongPollingBot {

    public static final String START_FAILED_MESSAGE = "Failed to register bot(check internet connection /" +
            " bot token or make sure only one instance of bot is running).";

    @Value("${telegram.bot.username}")
    private String botUsername;
    @Value("${telegram.bot.token}")
    private String botToken;

    private final GlobalUpdateHandler globalUpdateHandler;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @PostConstruct
    private void startBot(){
        try {
            log.info("Registering bot..");
            new TelegramBotsApi(DefaultBotSession.class).registerBot(this);
            log.info("Telegram Bot is ready to receive updates from users..");
        } catch (TelegramApiException e) {
            log.error(START_FAILED_MESSAGE, e);
            throw new RuntimeException(START_FAILED_MESSAGE, e);
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.info("Update received: updateId = " + update.getUpdateId());
        globalUpdateHandler.handle(update);
    }

}
