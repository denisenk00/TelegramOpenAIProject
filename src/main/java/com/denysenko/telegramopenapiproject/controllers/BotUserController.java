package com.denysenko.telegramopenapiproject.controllers;

import com.denysenko.telegramopenapiproject.exceptions.InputValidationException;
import com.denysenko.telegramopenapiproject.model.BotUser;
import com.denysenko.telegramopenapiproject.services.entityservices.BotUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class BotUserController {

    private final BotUserService botUserService;

    @GetMapping
    public ResponseEntity<Page<BotUser>> getPageOfBotUsers(@RequestParam(name = "page", defaultValue = "1", required = false) int pageNumber,
                                                           @RequestParam(name = "size", defaultValue = "30", required = false) int pageSize){
        if (pageNumber < 1 || pageSize < 1)
            throw new InputValidationException(String.format("Page number and it's size should be positive. Current params: pageNumber = %d, pageSize = %d", pageNumber, pageSize));

        var resultPage = botUserService.getPageOfBotUsers(pageNumber, pageSize);

        return ResponseEntity.ok(resultPage);
    }

}
