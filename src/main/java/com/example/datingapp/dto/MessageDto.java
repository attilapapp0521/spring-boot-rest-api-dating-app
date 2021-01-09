package com.example.datingapp.dto;

import com.example.datingapp.domain.Message;

import java.time.LocalDateTime;

public class MessageDto {
    private long id;
    private long senderId;
    private String senderUsername;
    private String senderPhotoUrl;
    private String content;
    private long recipientId;
    private String recipientUsername;
    private String recipientPhotoUrl;
    private LocalDateTime dateRead;
    private LocalDateTime messageSent;

    public MessageDto(Message message) {
        this.id = message.getId();
        this.senderId = message.getSender().getId();
        this.senderUsername = message.getSender().getUsername();
        this.content = message.getContent();
        this.recipientId = message.getRecipient().getId();
        this.recipientUsername = message.getRecipient().getUsername();
        this.dateRead = message.getDateRead();
        this.messageSent = message.getMessageSent();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getSenderPhotoUrl() {
        return senderPhotoUrl;
    }

    public void setSenderPhotoUrl(String senderPhotoUrl) {
        this.senderPhotoUrl = senderPhotoUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(long recipientId) {
        this.recipientId = recipientId;
    }

    public String getRecipientUsername() {
        return recipientUsername;
    }

    public void setRecipientUsername(String recipientUsername) {
        this.recipientUsername = recipientUsername;
    }

    public String getRecipientPhotoUrl() {
        return recipientPhotoUrl;
    }

    public void setRecipientPhotoUrl(String recipientPhotoUrl) {
        this.recipientPhotoUrl = recipientPhotoUrl;
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
}
