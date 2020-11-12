package com.deveducation.chat.controller;

import com.deveducation.chat.dto.ChatMessageDto;
import com.deveducation.chat.dto.MessageSender;
import com.deveducation.chat.entity.ChatMessage;
import com.deveducation.chat.entity.MessageStatus;
import com.deveducation.chat.service.MessageService;
import com.deveducation.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MessageController {
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/get-history", method = RequestMethod.POST)
    public List<ChatMessageDto> getHistory(@RequestBody MessageSender messageSender) {
        MessageStatus messageStatus = MessageStatus.privateMessage;
        if (messageSender.getMessageStatus()
                         .trim()
                         .equals("Groups")) {
            messageStatus = MessageStatus.publicMessage;
        } else {
            messageStatus = MessageStatus.privateMessage;
        }

        List<ChatMessage> history = null;
        List<ChatMessageDto> result = new ArrayList<>();
        int newMessageCount = 0;
        if (messageStatus.equals(MessageStatus.privateMessage)) {
            if (messageSender.getFrom() == null) {
                history = messageService.getPrivateMessage(getLoginCurrentUser(), messageSender.getLogin());
            } else {
                history = messageService.getPrivateMessage(messageSender.getFrom(), messageSender.getLogin());
            }
        } else {
            history = messageService.getGeneralMessage(messageSender.getLogin());
        }

        Integer indexOfStartNewMsg = null;
        for (int i = 0; i < history.size(); i++) {
            if (history.get(i)
                       .getMessage()
                       .equals("Нове повідомлення")) {
                indexOfStartNewMsg = i;
            }
        }

        if (indexOfStartNewMsg != null) {
            newMessageCount = history.size() - indexOfStartNewMsg;
        }
        for (ChatMessage chatMessage : history
        ) {
            result.add(new ChatMessageDto(chatMessage.getMessage(), newMessageCount == 0 ? 0 : newMessageCount - 1, chatMessage.getFrom(), chatMessage.getTo()));
        }
        return result;
    }

    @RequestMapping(value = "/clear-notification", method = RequestMethod.POST)
    public String deleteNotification(@RequestBody MessageSender messageSender) {
        return messageService.deleteNotificationAboutNewMessages(getLoginCurrentUser(), messageSender.getLogin()) ? "OK" : "ERROR";
    }

    private String getLoginCurrentUser() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String login = loggedInUser.getName();
        return login;
    }

}
