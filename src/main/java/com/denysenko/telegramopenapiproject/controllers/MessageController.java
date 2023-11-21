package com.denysenko.telegramopenapiproject.controllers;

import com.denysenko.telegramopenapiproject.exceptions.InputValidationException;
import com.denysenko.telegramopenapiproject.model.Message;
import com.denysenko.telegramopenapiproject.model.dto.AdminMessageDTO;
import com.denysenko.telegramopenapiproject.security.jwt.AuthenticatedUser;
import com.denysenko.telegramopenapiproject.services.entityservices.BotUserService;
import com.denysenko.telegramopenapiproject.services.entityservices.MessageService;
import com.denysenko.telegramopenapiproject.services.TelegramService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final TelegramService telegramService;
    private final BotUserService botUserService;

    @GetMapping("/last")
    public ResponseEntity<List<Message>> getLastNMessagesByBotUserId(
            @RequestParam(name = "userId") long botUserId,
            @RequestParam(name = "amount", defaultValue = "10", required = false) int amount){

        if(amount < 1) throw new InputValidationException(String.format("Amount should be positive, current = %d", amount));

        var messages = messageService.findLastNByBotUserId(botUserId, amount);

        return ResponseEntity.ok().body(messages);
    }

    @GetMapping
    public ResponseEntity<Page<Message>> getPageOfMessages(
            @RequestParam(name = "userId") long botUserId,
            @RequestParam(name = "page", defaultValue = "1", required = false) int pageNumber,
            @RequestParam(name = "size", defaultValue = "30", required = false) int pageSize){

        if (pageNumber < 1 || pageSize < 1)
            throw new InputValidationException(String.format("Page number and it's size should be positive. Current params: pageNumber = %d, pageSize = %d", pageNumber, pageSize));

        var page = messageService.getPageOfMessages(botUserId, pageNumber, pageSize);

        return ResponseEntity.ok(page);
    }

    @PostMapping
    public ResponseEntity sendMessage(@RequestBody @Valid AdminMessageDTO adminMessageDTO,
                                      @AuthenticationPrincipal AuthenticatedUser authenticatedUser){
        var botUser = botUserService.getById(adminMessageDTO.botUserId());
        if(!botUser.isActive())
            throw new InputValidationException(String.format("User profile with id = %d is inactive", adminMessageDTO.botUserId()));
        telegramService.sendMessage(botUser.getChatId(), adminMessageDTO.message());
        messageService.saveAdminMessage(adminMessageDTO, authenticatedUser);
        return ResponseEntity.ok().build();
    }


}
