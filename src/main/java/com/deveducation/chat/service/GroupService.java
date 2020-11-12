package com.deveducation.chat.service;

import com.deveducation.chat.dto.CustomUserDto;
import com.deveducation.chat.entity.CustomUser;
import com.deveducation.chat.entity.Group;
import com.deveducation.chat.repository.ChatMessageRepository;
import com.deveducation.chat.repository.CustomUserRepository;
import com.deveducation.chat.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {


    @Autowired
    UserService userService;

    @Autowired
    CustomUserRepository customUserRepository;


    @Autowired
    GroupRepository groupRepository;
    @Autowired
    ChatMessageRepository messageRepository;

    @Transactional
    public void createGroup(String groupName, String users, String loginCurrentUser) {

        CustomUser currentUser = userService.getUserByLogin(loginCurrentUser);
        List<CustomUser> customUserList = new ArrayList<>();
        String[] customUsers = users.trim().split(",");
        if (customUsers.length > 0) {
            for (int i = 0; i < customUsers.length; i++) {
                customUserList.add(userService.getUserByLogin(customUsers[i]));
            }
        }
        customUserList.add(userService.getUserByLogin(loginCurrentUser));
        Group group = new Group(groupName, loginCurrentUser, customUserList);

        currentUser.initGroup(group);
        if (customUsers.length > 0) {
            for (int i = 0; i < customUsers.length; i++) {
                userService.getUserByLogin(customUsers[i]).initGroup(group);
            }
        }
        groupRepository.save(group);
    }

    @Transactional
    public Group findGroupByName(String name) {
        return groupRepository.findByName(name);
    }


    @Transactional
    public List<CustomUserDto> getUsersByGroup(String groupName) {

        List<CustomUserDto> resultList = new ArrayList<>();
        Group group = groupRepository.findByName(groupName);
        List<CustomUser> usersFromGroup = group.getUsers();
        group.getOwnerLogin();
        for (CustomUser user : usersFromGroup
        ) {
            if (user.getLogin().equals(group.getOwnerLogin())) {
                resultList.add(new CustomUserDto(user.getLogin(), user.getAvatar(), true));
            } else {
                resultList.add(new CustomUserDto(user.getLogin(), user.getAvatar(), false));
            }

        }
        return resultList;
    }

    @Transactional
    public boolean deleteUserFromGroup(String groupName, String login) {
        Group group = groupRepository.findByName(groupName);
        if (group == null) {
            return false;
        }
        List<CustomUser> users = group.getUsers();
        CustomUser customUser = customUserRepository.findByLogin(login);
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getLogin().equals(login)) {

                users.remove(users.get(i));
                customUser.getGroup().remove(group);

            }
        }
        return true;
    }

    @Transactional
    public boolean deleteGroup(String groupName) {
        Group group = groupRepository.findByName(groupName);
        if (group == null) {
            return false;
        }
        List<CustomUser> users = group.getUsers();

        for (CustomUser user : users) {
            user.getGroup().remove(group);
        }
        groupRepository.delete(group);
        return true;
    }

    @Transactional
    public boolean addUserToGroup(String groupName, String users) {
        Group group = groupRepository.findByName(groupName);
        String[] userLogins = users.split(",");
        List<CustomUser> customUsers = new ArrayList<>();

        for (int i = 0; i < userLogins.length; i++) {
            customUsers.add(customUserRepository.findByLogin(userLogins[i]));
        }

        for (CustomUser user : customUsers
        ) {
            group.addUserToGroup(user);
            user.initGroup(group);
        }
        customUserRepository.saveAll(customUsers);
        groupRepository.save(group);
        return true;
    }

    @Transactional
    public List<CustomUserDto> getUnsignedUsers(String groupName, String loginCurrentUser) {

        List<CustomUserDto> users = new ArrayList<>();
        Group group = groupRepository.findByName(groupName);
        CustomUser customUser = customUserRepository.findByLogin(loginCurrentUser);

        List<CustomUser> savedUsers = new ArrayList<>(userService.getUsers(customUser));
        for (CustomUser user : savedUsers
        ) {
            if (user != null) {
                if (!user.getGroup().contains(group)) {
                    users.add(new CustomUserDto(user.getLogin(), user.getAvatar()));
                }
            }
        }
        return users;
    }

    @Transactional
    public Group changeGroupName(String groupName, String newName) {
        Group group = groupRepository.findByName(groupName);
        group.setName(newName);
        groupRepository.save(group);
        return group;
    }

    @Transactional
    public void clearNotificationInGroup(String groupName) {
        messageRepository.deleteAllByToAndMessage(groupName, "Нове повідомлення");
    }
}
