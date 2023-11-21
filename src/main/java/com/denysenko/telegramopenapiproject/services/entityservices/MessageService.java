package com.denysenko.telegramopenapiproject.services.entityservices;

import com.denysenko.telegramopenapiproject.model.Message;
import com.denysenko.telegramopenapiproject.model.MessageType;
import com.denysenko.telegramopenapiproject.model.dto.AdminMessageDTO;
import com.denysenko.telegramopenapiproject.repositories.AdminRepository;
import com.denysenko.telegramopenapiproject.repositories.MessageRepository;
import com.denysenko.telegramopenapiproject.security.jwt.AuthenticatedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MessageService {

    private final MessageRepository messageRepository;
    private final BotUserService botUserService;
    private final AdminRepository adminRepository;

    @Transactional
    public Message saveRequest(Long chatId, String text){
        var botUser = botUserService.getByChatId(chatId);
        var message = new Message(botUser, text, MessageType.REQUEST);
        return messageRepository.save(message);
    }

    @Transactional
    public Message saveResponse(Long chatId, String text){
        var botUser = botUserService.getByChatId(chatId);
        var message = new Message(botUser, text, MessageType.RESPONSE);
        return messageRepository.save(message);
    }

    @Transactional
    public Message saveAdminMessage(AdminMessageDTO adminMessageDTO, AuthenticatedUser authenticatedUser){
        var admin = adminRepository.findByUsername(authenticatedUser.getUsername()).get();
        var botUser = botUserService.getById(adminMessageDTO.botUserId());
        var message = new Message(botUser, adminMessageDTO.message(), MessageType.RESPONSE, admin);
        return messageRepository.save(message);
    }

    public Page<Message> getPageOfMessages(Long botUserId, int pageNumber, int size){
        if(pageNumber < 1 || size < 1) throw new IllegalArgumentException("Page number and size should be positive");
        var request = PageRequest.of(pageNumber - 1, size);
        return messageRepository.findAllByBotUserIdOrderByTimeDesc(botUserId, request);
    }

    public List<Message> findLastNByBotUserId(Long botUserId, int size){
        return getPageOfMessages(botUserId, 1, size).getContent();
    }



}
