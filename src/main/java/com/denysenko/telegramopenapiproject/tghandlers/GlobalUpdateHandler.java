package com.denysenko.telegramopenapiproject.tghandlers;

import com.denysenko.telegramopenapiproject.exceptions.TelegramBotException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Set;

@Log4j
@RequiredArgsConstructor
@Component
public class GlobalUpdateHandler {

    private final Set<Handler> handlers;

    public void handle(Update update) {
        log.info("Handled update: updateId = " + update.getUpdateId().toString());
        try {
            handlers.stream()
                    .filter(handler -> handler.isApplicable(update))
                    .findFirst()
                    .ifPresent(handler -> handler.handle(update));
        }catch (Exception e){
            throw new TelegramBotException(e.getMessage(), e);
        }
    }

}
