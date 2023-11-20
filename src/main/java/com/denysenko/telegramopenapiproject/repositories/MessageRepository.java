package com.denysenko.telegramopenapiproject.repositories;

import com.denysenko.telegramopenapiproject.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

    Page<Message> findAllByBotUserIdOrderByTimeDesc(Long botUserId, Pageable pageable);

}
