package com.example.datingapp.controller;

import com.example.datingapp.dto.CreateMessageDto;
import com.example.datingapp.dto.MessageDto;
import com.example.datingapp.extension.HttpExtension;
import com.example.datingapp.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<MessageDto> createMessage(@RequestBody CreateMessageDto createMessageDto){
        logger.info("Message saving from user...");
       return messageService.createMessage(createMessageDto);
    }

    @GetMapping
    public ResponseEntity<List<MessageDto>> getMessagesForUsers(String container, Pageable pageable){
        logger.info("Get messaging...");
        Page<MessageDto> users = messageService.getMessagesForUsers(container, pageable);
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpExtension.addPaginationHeader(httpHeaders,users.getNumber(),
                users.getNumberOfElements(), users.getTotalElements(),
                users.getTotalPages());

        return ResponseEntity.ok().headers(httpHeaders).body(users.getContent());
    }

    @GetMapping("thread/{username}")
    public ResponseEntity<List<MessageDto>> getMessageThread(@PathVariable String username) {
        logger.info("Get thread message...");
      return new ResponseEntity<>(messageService.getMessageThread(username),OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long id){
        logger.info("Delete is in progress...");
       return messageService.deleteMessage(id);
    }
}
