package com.deveducation.chat.service;


import com.deveducation.chat.entity.ChatMessage;
import com.deveducation.chat.entity.CustomUser;
import com.deveducation.chat.entity.Group;
import com.deveducation.chat.entity.MessageStatus;
import com.deveducation.chat.repository.ChatMessageRepository;
import com.deveducation.chat.repository.CustomUserRepository;
import com.deveducation.chat.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    ChatMessageRepository chatMessageRepository;

    @Autowired
    CustomUserRepository customUserRepository;

    @Autowired
    GroupService groupService;

    @Autowired
    GroupRepository groupRepository;

    @Transactional
    public void saveMessage(CustomUser currentUser, String to, String message, MessageStatus messageStatus) {
        Group group = null;
        ChatMessage msg = null;
        ChatMessage newMessageNotification = null;
        if (messageStatus.equals(MessageStatus.publicMessage)) {
            group = groupService.findGroupByName(to);

            if (chatMessageRepository.findByTo(to).stream().anyMatch(chatMessage -> chatMessage.getMessage().equals("Нове повідомлення"))) {
                msg = new ChatMessage(message, to, currentUser, messageStatus, group);
                if (group != null)
                    group.addToChats(msg);
            } else {
                newMessageNotification = new ChatMessage("Нове повідомлення", to, currentUser, messageStatus);
                msg = new ChatMessage(message, to, currentUser, messageStatus);
                if (group != null) {
                    group.addToChats(newMessageNotification);
                    group.addToChats(msg);
                }
            }
        } else {
            if (chatMessageRepository.findByCustomUser(currentUser).contains(new ChatMessage("Нове повідомлення", to, currentUser, messageStatus))) {
                msg = new ChatMessage(message, to, currentUser, messageStatus);
            } else {
                newMessageNotification = new ChatMessage("Нове повідомлення", to, currentUser, messageStatus);
                msg = new ChatMessage(message, to, currentUser, messageStatus);
            }
        }
        if (newMessageNotification != null) {
            chatMessageRepository.save(newMessageNotification);
        }
        chatMessageRepository.save(msg);


        if (group != null)
            groupRepository.save(group);
    }

    @Transactional
    public List<ChatMessage> getPrivateMessage(String loginFirstUser, String loginSecondUser) {

        List<ChatMessage> chatMessages = new ArrayList<>();

        CustomUser firstUser = customUserRepository.findByLogin(loginFirstUser);
        CustomUser secondUser = customUserRepository.findByLogin(loginSecondUser);

        chatMessages.addAll(chatMessageRepository.findByCustomUserAndTo(firstUser, loginSecondUser));
        chatMessages.addAll(chatMessageRepository.findByCustomUserAndTo(secondUser, loginFirstUser));

        chatMessages.sort((o1, o2) -> {
            if (o1.getDate() == null || o2.getDate() == null)
                return 0;
            return o1.getDate().compareTo(o2.getDate());
        });

        return chatMessages;
    }

    @Transactional
    public boolean deleteNotificationAboutNewMessages(String loginFirstUser, String loginSecondUser) {
        List<ChatMessage> fromMess = chatMessageRepository.deleteAllByFromAndToAndMessage(loginSecondUser, loginFirstUser, "Нове повідомлення");
        List<ChatMessage> toMess = chatMessageRepository.deleteAllByFromAndToAndMessage(loginFirstUser, loginSecondUser, "Нове повідомлення");
        return false;
    }

    @Transactional
    public List<ChatMessage> getGeneralMessage(String nameOfGroup) {
        Group group = groupService.findGroupByName(nameOfGroup);
        List<ChatMessage> generalMessage = group.getChatMessage();

        Collections.sort(generalMessage, (o1, o2) -> {
            if (o1.getDate() == null || o2.getDate() == null)
                return 0;
            return o1.getDate().compareTo(o2.getDate());
        });
        return generalMessage;
    }
}
