package com.denysenko.telegramopenapiproject.services.entityservices;

import com.denysenko.telegramopenapiproject.exceptions.BotUserNotFoundException;
import com.denysenko.telegramopenapiproject.model.BotUser;
import com.denysenko.telegramopenapiproject.repositories.BotUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BotUserService {

    private final BotUserRepository botUserRepository;

    public BotUser getByChatId(Long chatId){
        return botUserRepository.findByChatId(chatId)
                .orElseThrow(() -> new BotUserNotFoundException(chatId, String.format("Bot user with chatId = %d wasn't found", chatId)));
    }

    public BotUser getById(Long id){
        return botUserRepository.findById(id)
                .orElseThrow(() -> new BotUserNotFoundException(String.format("Bot user with id = %d wasn't found", id)));
    }

    public Page<BotUser> getPageOfBotUsers(int pageNumber, int size){
        if(pageNumber < 1 || size < 1) throw new IllegalArgumentException("Page number and size should be positive");

        var request = PageRequest.of(pageNumber - 1, size);
        return botUserRepository.findAllBy(request);
    }

    @Transactional
    public void changeActivity(Long chatId, boolean isActive) {
        botUserRepository.changeActivity(chatId, isActive);
    }

    public Optional<BotUser> findBotUserByChatId(Long chatId) {
        return botUserRepository.findByChatId(chatId);
    }

    @Transactional
    public void save(String username, Long chatId, boolean isActive){
        BotUser botUser = new BotUser(chatId, username, isActive);
        botUserRepository.save(botUser);
    }
}
