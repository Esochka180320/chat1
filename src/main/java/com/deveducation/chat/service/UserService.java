package com.deveducation.chat.service;

import com.deveducation.chat.dto.CustomUserDto;
import com.deveducation.chat.dto.GroupDataDto;
import com.deveducation.chat.entity.ChatMessage;
import com.deveducation.chat.entity.CustomUser;
import com.deveducation.chat.entity.Group;
import com.deveducation.chat.entity.MessageStatus;
import com.deveducation.chat.repository.ChatMessageRepository;
import com.deveducation.chat.repository.CustomUserRepository;
import com.deveducation.chat.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class UserService {


    @Autowired
    ChatMessageRepository chatMessageRepository;


    @Autowired
    CustomUserRepository customUserRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    MessageService messageService;
    @Transactional
    public List<CustomUser> getAllUser() {
        return customUserRepository.findAll();
    }


    @Transactional
    public boolean saveUser(CustomUser customUser) {

        if (customUserRepository.existsByLogin(customUser.getLogin())) {
            return false;
        }
        customUserRepository.save(customUser);
        return true;
    }


    @Transactional
    public CustomUser getUserByLogin(String login) {

        return customUserRepository.findByLogin(login);

    }

    @Transactional
    public CustomUser addUser(CustomUser customUser) {

        return customUserRepository.save(customUser);

    }


    @Transactional
    public List<String> getAllNetworks(CustomUser customUser) {


        List<String> networks = new LinkedList<>();

        if (customUser.getFacebook() != null) {
            networks.add(customUser.getFacebook());
        } else networks.add("");

        if (customUser.getTwitter() != null) {
            networks.add(customUser.getTwitter());
        } else networks.add("");

        if (customUser.getInstagram() != null) {
            networks.add(customUser.getInstagram());
        } else networks.add("");

        return networks;

    }


    public String getNetworkUrl(String owner, String nameOfTheNetwork) {

        CustomUser customUser = customUserRepository.findByLogin(owner);
        String url = "";

        if (nameOfTheNetwork.trim().equals("facebook")) {
            url = customUser.getFacebook();
        } else if (nameOfTheNetwork.trim().equals("twitter")) {
            url = customUser.getTwitter();
        } else if (nameOfTheNetwork.trim().equals("instagram")) {
            url = customUser.getInstagram();
        }

        return url;
    }

    @Transactional
    public Set<CustomUser> getUsers(CustomUser currentUser) {

        Set<CustomUser> customUsers = new HashSet<>();
        List<ChatMessage> allMessages = chatMessageRepository.findByMessageStatus(MessageStatus.privateMessage);


        for (ChatMessage allMessage : allMessages) {

            if (getUserByLogin(allMessage.getTo()).equals(currentUser)) {
                customUsers.add(allMessage.getCustomUser());
            }
        }

        List<ChatMessage> messages = chatMessageRepository.findByCustomUser(currentUser);

        customUsers.add(currentUser);

        for (ChatMessage message : messages) {

            customUsers.add(getUserByLogin(message.getTo()));

        }


        return customUsers;

    }


    @Transactional
    public List<CustomUserDto> getSavedUsers(CustomUser currentUser) {

        Set<CustomUser> customUsers = new HashSet<>();
        List<ChatMessage> allMessages = chatMessageRepository.findByMessageStatus(MessageStatus.privateMessage);

        int newMessageCount = 0;
        for (ChatMessage allMessage : allMessages) {

            if (getUserByLogin(allMessage.getTo()).equals(currentUser)) {
                customUsers.add(allMessage.getCustomUser());
            }
        }

        List<ChatMessage> messages = chatMessageRepository.findByCustomUser(currentUser);

        customUsers.add(currentUser);

        for (ChatMessage message : messages) {

            customUsers.add(getUserByLogin(message.getTo()));

        }

        List<CustomUser> preResult = new ArrayList<>(customUsers);

        List<CustomUserDto> result = new ArrayList<>();

        Integer indexOfStartNewMsg = null;
        for (int i = 0; i < preResult.size(); i++) {
            newMessageCount=0;
            indexOfStartNewMsg=null;
            for (int j = 0; j <  chatMessageRepository.findByCustomUserAndTo(preResult.get(i),currentUser.getLogin()).size(); j++) {

                if (chatMessageRepository.findByCustomUserAndTo(preResult.get(i),currentUser.getLogin()).get(j).getTo().equals(currentUser.getLogin()) &&
                        chatMessageRepository.findByCustomUserAndTo(preResult.get(i),currentUser.getLogin()).get(j).getMessage().equals("Нове повідомлення")) {
                    indexOfStartNewMsg = j+1;
                    break;
                }

            }

            if (indexOfStartNewMsg != null) {
                int allCountOfMessage=chatMessageRepository.findByCustomUserAndTo(preResult.get(i),currentUser.getLogin()).size();
                newMessageCount = allCountOfMessage - indexOfStartNewMsg;
            }
            if (preResult.get(i)!=null)
                result.add(new CustomUserDto(preResult.get(i).getAvatar(), preResult.get(i).getLogin(), newMessageCount));
        }

        result.sort((o1, o2) -> {
            if (o1.getCountOfNewMessages() == null || o2.getCountOfNewMessages() == null)
                return 0;
            return o1.getCountOfNewMessages().compareTo(o2.getCountOfNewMessages());
        });
        Collections.reverse(result);
        return result;

    }


    @Transactional
    public Set<CustomUser> searchUser(String login) {

        return customUserRepository.findByLoginStartsWith(login);


    }

    @Transactional
    public List<GroupDataDto> getSavedGroups(CustomUser currentUser) {

        List<Group> groups = new LinkedList<>(currentUser.getGroup());
        List<GroupDataDto> result = new LinkedList<>();

        for (Group group: groups
        ) {
            List<ChatMessage> msgs = messageService.getGeneralMessage(group.getName());

            if (msgs != null && msgs.size() > 0) {
                result.add(new GroupDataDto(group.getName(),msgs.get(msgs.size()-1).getMessage(),msgs.get(msgs.size()-1).getFrom()));
            }else {
                result.add(new GroupDataDto(group.getName(),"",""));

            }
        }

        return result ;

    }

    @Transactional
    public Set<CustomUserDto> searchUsersToAddingToGroup(String login, String groupName) {

        Set<CustomUserDto> result = new LinkedHashSet<>();
        Group group = groupRepository.findByName(groupName);


        Set<CustomUser> allUsers = customUserRepository.findByLoginStartsWith(login);

        for (CustomUser customUser : allUsers
        ) {
            if ((customUser.getGroup() == null || !customUser.getGroup().contains(group)))
                result.add(new CustomUserDto(customUser.getLogin(), customUser.getAvatar()));

        }


        return result;

    }

    public boolean leaveGroup(String loginCurrentUser, String groupName) {

        CustomUser currentUser = getUserByLogin(loginCurrentUser);
        Group group = groupRepository.findByName(groupName);

        if (currentUser == null || group == null) {
            return false;
        }

        group.getUsers().remove(currentUser);
        currentUser.getGroup().remove(group);
        groupRepository.save(group);
        customUserRepository.save(currentUser);

        return true;
    }


    private String getLoginCurrentUser() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String login = loggedInUser.getName();

        return login;
    }


}
