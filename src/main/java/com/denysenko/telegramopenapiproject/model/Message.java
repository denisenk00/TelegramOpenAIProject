package com.denysenko.telegramopenapiproject.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;
    @ManyToOne()
    @JoinColumn(name = "botuser_id", updatable = false)
    private BotUser botUser;
    @Column(name = "text", columnDefinition="TEXT", nullable = false, updatable = false)
    private String text;
    @CreationTimestamp
    @Column(name = "time", nullable = false, updatable = false)
    private LocalDateTime time;
    @Column(name = "message_type", nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private MessageType messageType;
    @ManyToOne
    @JoinColumn(name = "admin_id", updatable = false)
    private Admin admin;

    public Message(BotUser botUser, String text, MessageType messageType, Admin admin) {
        this.botUser = botUser;
        this.text = text;
        this.messageType = messageType;
        this.admin = admin;
    }
    public Message(BotUser botUser, String text, MessageType messageType) {
        this.botUser = botUser;
        this.text = text;
        this.messageType = messageType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return this.id != null && this.id.equals(message.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
