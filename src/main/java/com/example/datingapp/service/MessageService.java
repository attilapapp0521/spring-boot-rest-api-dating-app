package com.example.datingapp.service;

import com.example.datingapp.domain.Message;
import com.example.datingapp.domain.User;
import com.example.datingapp.dto.CreateMessageDto;
import com.example.datingapp.dto.MessageDto;
import com.example.datingapp.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            logger.warn("Not found recipient");
            return new ResponseEntity<>(NOT_FOUND);
        } else if (sender.getUsername().equals(createMessageDto.getRecipientUsername())) {
            logger.warn("The recipient and the sender is same user ");
            return new ResponseEntity("You cannot send messages to yourself", BAD_REQUEST);
        }

        Message message = new Message(sender,recipient,createMessageDto.getContent());
        messageRepository.save(message);

        logger.info("The message has saved");
        return new ResponseEntity<>(getMessageDto(message), CREATED);
    }

    private MessageDto getMessageDto(Message message){
        MessageDto messageDto = new MessageDto(message);
        messageDto.setSenderPhotoUrl((String) userService.getPhotos(message.getSender()).getSecond());
        messageDto.setRecipientPhotoUrl((String) userService.getPhotos(message.getRecipient()).getSecond());

        return messageDto;
    }


    public Page<MessageDto> getMessagesForUsers(String container, Pageable pageable) {
        String username = userService.getAuthenticatedUserName();

        if(container != null && container.equals("Inbox")){
            logger.info("Get inbox messaging...");
            Page<Message> messages = messageRepository.getMessagesForUserInbox(username, pageable);
            return messages.map(this::getMessageDto);
        }else if(container != null && container.equals("Outbox")){
            logger.info("Get outbox messaging...");
            Page<Message> messages = messageRepository.getMessagesForUserOutbox(username, pageable);
            return messages.map(this::getMessageDto);
        }
        logger.info("Get unread messaging...");
        Page<Message> messages = messageRepository.getMessagesForUserUnread(username, pageable);
        return messages.map(this::getMessageDto);
    }

    public List<MessageDto> getMessageThread(String recipientUsername) {
        String currentUsername = userService.getAuthenticatedUserName();
        setReadMessages(currentUsername);

       List<MessageDto> messageDtoList = new ArrayList<>();
       List<Message> messages = messageRepository.getMessageThread(currentUsername,recipientUsername);
        for(Message message : messages){
            messageDtoList.add(new MessageDto(message));
        }

        return messageDtoList;
    }

    private void setReadMessages(String username){
        List<Message> messages = messageRepository.findAll();
        for(Message message : messages){
            if(message.getDateRead() == null &&
                    message.getRecipient().getUsername().equals(username)){
                message.setDateRead(LocalDateTime.now());
                messageRepository.save(message);
                logger.info("Set message read in " + message.getDateRead());
            }
        }

    }

    public ResponseEntity<Void> deleteMessage(Long id) {
        String username = userService.getAuthenticatedUserName();
        Message message = messageRepository.getById(id);
        if(message == null){
            logger.info("Message was not found");
            return new ResponseEntity<>(NOT_FOUND);
        }else if(!message.getSender().getUsername().equals(username)
            && !message.getRecipient().getUsername().equals(username)){
            logger.info("Message is not possible this user");
            return new ResponseEntity<>(UNAUTHORIZED);
        }

        if(message.getSender().getUsername().equals(username)){
            message.setSenderDeleted(true);
            logger.info("Sender has deleted the message");
        }else if(message.getRecipient().getUsername().equals(username)){
            message.setRecipientDeleted(true);
            logger.info("Recipient has deleted the message");
        }

        if(message.isSenderDeleted() && message.isRecipientDeleted()){
            messageRepository.delete(message);
            logger.info("Message is deleted in database");
        }

        return new ResponseEntity<>(OK);
    }
}
