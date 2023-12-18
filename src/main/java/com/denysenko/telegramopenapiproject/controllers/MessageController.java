package com.denysenko.telegramopenapiproject.controllers;

import com.denysenko.telegramopenapiproject.controllers.api.MessageAPI;
import com.denysenko.telegramopenapiproject.exceptions.InputValidationException;
import com.denysenko.telegramopenapiproject.model.Message;
import com.denysenko.telegramopenapiproject.model.dto.AdminMessageDTO;
import com.denysenko.telegramopenapiproject.security.jwt.AuthenticatedUser;
import com.denysenko.telegramopenapiproject.services.entityservices.BotUserService;
import com.denysenko.telegramopenapiproject.services.entityservices.MessageService;
import com.denysenko.telegramopenapiproject.services.TelegramService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MessageController implements MessageAPI {

    private final MessageService messageService;
    private final TelegramService telegramService;
    private final BotUserService botUserService;

    @Override
    public ResponseEntity<List<Message>> getLastNMessagesByBotUserId(long botUserId, int amount){

        if(amount < 1) throw new InputValidationException(String.format("Amount should be positive, current = %d", amount));

        var messages = messageService.findLastNByBotUserId(botUserId, amount);

        return ResponseEntity.ok().body(messages);
    }

    @Override
    public ResponseEntity<Page<Message>> getPageOfMessages(long botUserId, int pageNumber, int pageSize){

        if (pageNumber < 1 || pageSize < 1)
            throw new InputValidationException(String.format("Page number and it's size should be positive. Current params: pageNumber = %d, pageSize = %d", pageNumber, pageSize));

        var page = messageService.getPageOfMessages(botUserId, pageNumber, pageSize);

        return ResponseEntity.ok(page);
    }

    @Override
    public ResponseEntity sendMessage(AdminMessageDTO adminMessageDTO, AuthenticatedUser authenticatedUser){
        var botUser = botUserService.getById(adminMessageDTO.botUserId());
        if(!botUser.isActive())
            throw new InputValidationException(String.format("User profile with id = %d is inactive", adminMessageDTO.botUserId()));
        telegramService.sendMessage(botUser.getChatId(), adminMessageDTO.message());
        messageService.saveAdminMessage(adminMessageDTO, authenticatedUser);
        return ResponseEntity.ok().build();
    }


}
