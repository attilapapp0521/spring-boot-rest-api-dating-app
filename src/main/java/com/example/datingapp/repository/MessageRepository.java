package com.example.datingapp.repository;

import com.example.datingapp.domain.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.DoubleStream;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {


    @Query("SELECT m FROM Message m WHERE m.recipient.username = :username " +
            "AND m.recipientDeleted = false ORDER BY m.messageSent DESC ")
    Page<Message> getMessagesForUserInbox(@Param("username") String username,
                                           Pageable pageable);
    @Query("SELECT m FROM Message m WHERE m.sender.username = :username " +
            "AND m.senderDeleted = false ORDER BY m.messageSent DESC")
    Page<Message> getMessagesForUserOutbox(@Param("username") String username,
                                           Pageable pageable);
    @Query("SELECT m FROM Message m WHERE m.recipient.username = :currentUsername " +
            "   AND m.sender.username = :recipientUsername" +
            "   AND m.recipientDeleted = false " +
            "OR m.recipient.username = :recipientUsername " +
            "   AND m.sender.username = :currentUsername " +
            "   AND m.senderDeleted = false " +
            "ORDER BY m.messageSent")
    List<Message> getMessageThread(@Param("currentUsername") String currentUsername,
                                   @Param("recipientUsername") String recipientUsername);


    @Query("SELECT m FROM Message m WHERE m.recipient.username = :username " +
            "AND m.dateRead IS NULL OR m.dateRead = '0-0-0 0:0:0' ")
    Page<Message> getMessagesForUserUnread(@Param("username") String username,
                                           Pageable pageable);
    @Query("SELECT m FROM Message m WHERE m.id = :id")
    Message getById(@Param("id") Long id);
}
