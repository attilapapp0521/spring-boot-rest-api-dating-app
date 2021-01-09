package com.example.datingapp.controller;

import com.example.datingapp.dto.CreateMessageDto;
import com.example.datingapp.dto.MessageDto;
import com.example.datingapp.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<MessageDto> createMessage(@RequestBody CreateMessageDto createMessageDto){
       return messageService.createMessage(createMessageDto);
    }
}
