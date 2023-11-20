package com.denysenko.telegramopenapiproject.tghandlers;

import com.denysenko.telegramopenapiproject.model.BotUser;
import com.denysenko.telegramopenapiproject.services.OpenAIService;
import com.denysenko.telegramopenapiproject.services.TelegramService;
import com.denysenko.telegramopenapiproject.services.entityservices.BotUserService;
import com.denysenko.telegramopenapiproject.services.entityservices.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TextHandler extends Handler{

    private static final String START_COMMAND = "/start";
    private static final String START_MESSAGE = "Hi! Glad to see yoo here. I'm waiting for your first request)";
    private final BotUserService botUserService;
    private final TelegramService telegramService;
    private final MessageService messageService;
    private final OpenAIService openAIService;

    @Override
    public boolean isApplicable(Update update) {
        return update.hasMessage() && update.getMessage().hasText();
    }

    @Override
    public void handle(Update update) {
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String text = message.getText();

        if(text.equals(START_COMMAND)){
            telegramService.sendMessage(chatId, START_MESSAGE);
            Optional<BotUser> botUser = botUserService.findBotUserByChatId(chatId);
            botUser.ifPresentOrElse(b -> botUserService.changeActivity(chatId, true),
                    () -> {
                        String username = message.getFrom().getUserName();
                        botUserService.save(username, chatId, true);
                    });
        }else {
            messageService.saveRequest(chatId, text);
            var response = openAIService.executeTextRequest(text);
            messageService.saveResponse(chatId, response);
            telegramService.sendMessage(chatId,response);
        }

    }
}
