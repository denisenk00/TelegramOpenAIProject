package com.denysenko.telegramopenapiproject.repositories;

import com.denysenko.telegramopenapiproject.model.BotUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BotUserRepository extends JpaRepository<BotUser, Long> {

    BotUser save(BotUser entity);

    @Modifying
    @Query("update BotUser b set b.isActive = :isActive where b.chatId = :chatId")
    void changeActivity(@Param("chatId") Long chatId, @Param("isActive") boolean isActive);

    Optional<BotUser> findByChatId(Long chatId);

    Page<BotUser> findAllBy(PageRequest pageRequest);

}
