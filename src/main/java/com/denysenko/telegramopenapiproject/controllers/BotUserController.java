package com.denysenko.telegramopenapiproject.controllers;

import com.denysenko.telegramopenapiproject.controllers.api.BotUserAPI;
import com.denysenko.telegramopenapiproject.exceptions.InputValidationException;
import com.denysenko.telegramopenapiproject.model.BotUser;
import com.denysenko.telegramopenapiproject.services.entityservices.BotUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class BotUserController implements BotUserAPI {

    private final BotUserService botUserService;

    @Override
    public ResponseEntity<Page<BotUser>> getPageOfBotUsers(int pageNumber, int pageSize){
        if (pageNumber < 1 || pageSize < 1)
            throw new InputValidationException(String.format("Page number and it's size should be positive. Current params: pageNumber = %d, pageSize = %d", pageNumber, pageSize));

        var resultPage = botUserService.getPageOfBotUsers(pageNumber, pageSize);

        return ResponseEntity.ok(resultPage);
    }

}
