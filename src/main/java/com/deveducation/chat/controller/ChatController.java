package com.deveducation.chat.controller;

import com.deveducation.chat.dto.MessageSaver;
import com.deveducation.chat.entity.CustomUser;
import com.deveducation.chat.entity.MessageStatus;
import com.deveducation.chat.repository.CustomUserRepository;
import com.deveducation.chat.service.MessageService;
import com.deveducation.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @Autowired
    UserService userService;
    @Autowired
    MessageService messageService;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CustomUserRepository userRepository;

    @MessageMapping("/message")
    @SendTo("/chat/messages")
    public MessageSaver getMessages(@Payload MessageSaver messageSaver) {
        CustomUser currentUser = userService.getUserByLogin(messageSaver.getFrom());
        CustomUser to = userService.getUserByLogin(messageSaver.getTo());

        if (messageSaver.getFrom()
                        .equals("LeftGroupBot") && currentUser == null) {
            currentUser = userRepository.save(new CustomUser("LeftGroupBot"));
        } else if (messageSaver.getFrom()
                               .equals("AddedGroupBot") && currentUser == null) {

            currentUser = userRepository.save(new CustomUser("AddedGroupBot"));
        } else if (messageSaver.getFrom()
                               .equals("DeletedGroupBot") && currentUser == null) {

            currentUser = userRepository.save(new CustomUser("DeletedGroupBot"));
        }
        MessageStatus messageStatus = messageSaver.getMessageStatus()
                                                  .trim()
                                                  .toLowerCase()
                                                  .equals("groups") ? MessageStatus.publicMessage : MessageStatus.privateMessage;

        messageService.saveMessage(currentUser, messageSaver.getTo(), messageSaver.getMessage(), messageStatus);
        return messageSaver;
    }

    private String getLoginCurrentUser() {
        Authentication loggedInUser = SecurityContextHolder.getContext()
                                                           .getAuthentication();
        String login = loggedInUser.getName();
        return login;
    }



}

