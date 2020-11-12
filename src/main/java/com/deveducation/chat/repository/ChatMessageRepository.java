package com.deveducation.chat.repository;

import com.deveducation.chat.entity.ChatMessage;
import com.deveducation.chat.entity.CustomUser;
import com.deveducation.chat.entity.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {


    List<ChatMessage> findByCustomUserAndTo(CustomUser customUser, String to);
    List<ChatMessage> findByCustomUser(CustomUser customUser);


    List<ChatMessage> findByMessageStatus(MessageStatus privateMessage);
    List<ChatMessage> deleteAllByFromAndToAndMessage(String from, String to,String message);
    List<ChatMessage> findByTo( String to );
    List<ChatMessage> deleteAllByToAndMessage( String to, String message);


    List<ChatMessage> findByFromAndToAndMessage(String loginFirstUser, String loginSecondUser, String newMessage);
}
