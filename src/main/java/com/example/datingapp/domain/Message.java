package com.example.datingapp.domain;

import com.example.datingapp.dto.CreateMessageDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private User sender;
    @ManyToOne
    private User recipient;
    private String content;
    private LocalDateTime dateRead;
    private LocalDateTime messageSent;
    private boolean senderDeleted;
    private boolean recipientDeleted;

    public Message() {
    }

    public Message(User sender, User recipient, String content) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.messageSent = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDateRead() {
        return dateRead;
    }

    public void setDateRead(LocalDateTime dateRead) {
        this.dateRead = dateRead;
    }

    public LocalDateTime getMessageSent() {
        return messageSent;
    }

    public void setMessageSent(LocalDateTime messageSent) {
        this.messageSent = messageSent;
    }

    public boolean isSenderDeleted() {
        return senderDeleted;
    }

    public void setSenderDeleted(boolean senderDeleted) {
        this.senderDeleted = senderDeleted;
    }

    public boolean isRecipientDeleted() {
        return recipientDeleted;
    }

    public void setRecipientDeleted(boolean recipientDeleted) {
        this.recipientDeleted = recipientDeleted;
    }
}
