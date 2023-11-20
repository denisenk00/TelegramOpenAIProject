package com.denysenko.telegramopenapiproject.tghandlers;

import com.denysenko.telegramopenapiproject.services.entityservices.BotUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.ChatMemberUpdated;
import org.telegram.telegrambots.meta.api.objects.Update;

@Log4j
@RequiredArgsConstructor
@Component
public class ChatMemberChangeHandler extends Handler{

    private final BotUserService botUserService;

    @Override
    public boolean isApplicable(Update update) {
        return update.hasMyChatMember();
    }

    @Override
    public void handle(Update update) {
        log.info("Update handled by ChatMemberHandler: updateID = " + update.getUpdateId());
        ChatMemberUpdated chatMemberUpdated = update.getMyChatMember();
        String newStatus = chatMemberUpdated.getNewChatMember().getStatus();
        if(newStatus.equals("kicked")) {
            log.info("User kicked the bot from chat");
            Long chatId = chatMemberUpdated.getFrom().getId();
            botUserService.changeActivity(chatId, false);
        }
    }
}
