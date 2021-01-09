package com.example.datingapp.service;

import com.example.datingapp.domain.Message;
import com.example.datingapp.domain.User;
import com.example.datingapp.dto.CreateMessageDto;
import com.example.datingapp.dto.MessageDto;
import com.example.datingapp.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static org.springframework.http.HttpStatus.*;

@Service
@Transactional
public class MessageService {
    private final UserService userService;
    private final MessageRepository messageRepository;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public MessageService(UserService userService, MessageRepository messageRepository) {
        this.userService = userService;
        this.messageRepository = messageRepository;
    }

    public ResponseEntity<MessageDto> createMessage(CreateMessageDto createMessageDto) {
        User sender = userService.findUserByUsername(userService.getAuthenticatedUserName());
        User recipient = userService.findUserByUsername(createMessageDto.getRecipientUsername());

        if (recipient == null) {
            return new ResponseEntity<>(NOT_FOUND);
        } else if (sender.getUsername().equals(createMessageDto.getRecipientUsername())) {
            return new ResponseEntity("You cannot send messages to yourself", BAD_REQUEST);
        }

        Message message = new Message(sender,recipient,createMessageDto.getContent());
        messageRepository.save(message);

        return new ResponseEntity<>(getMessageDto(sender,recipient,message), CREATED);
    }

    private MessageDto getMessageDto(User sender, User recipient, Message message){
        MessageDto messageDto = new MessageDto(message);
        messageDto.setSenderPhotoUrl((String) userService.getPhotos(sender).getSecond());
        messageDto.setRecipientPhotoUrl((String) userService.getPhotos(recipient).getSecond());

        return messageDto;
    }


}
