package com.denysenko.telegramopenapiproject.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "bot_users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BotUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "botuser_id")
    private Long id;
    @NaturalId
    @Column(name = "chat_id")
    private Long chatId;
    @Column(name = "name")
    private String name;
    @Column(name = "is_active")
    private boolean isActive;

    public BotUser(Long chatId, String name, boolean isActive) {
        this.chatId = chatId;
        this.name = name;
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BotUser botUser = (BotUser) o;
        return this.id != null && this.id.equals(botUser.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
