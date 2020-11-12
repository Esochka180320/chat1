package com.deveducation.chat.controller;

import com.deveducation.chat.dto.CustomUserDto;
import com.deveducation.chat.dto.GroupDataDto;
import com.deveducation.chat.dto.MessageSender;
import com.deveducation.chat.dto.NetworkData;
import com.deveducation.chat.entity.ChatMessage;
import com.deveducation.chat.entity.CustomUser;
import com.deveducation.chat.service.MessageService;
import com.deveducation.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@RestController
public class UserController {
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/get-all-users", method = RequestMethod.POST)
    public List<CustomUserDto> getAllUsers() {
        CustomUser currentUser = userService.getUserByLogin(getLoginCurrentUser());
        List<CustomUserDto> users = userService.getSavedUsers(currentUser);
        for (CustomUserDto usr : users
        ) {
            List<ChatMessage> msgs = messageService.getPrivateMessage(currentUser.getLogin(), usr.getLogin());
            if (msgs.size() > 0) {
                usr.setLastMessage(msgs.get(msgs.size() - 1)
                                       .getMessage());
                usr.setLastMessageFrom(msgs.get(msgs.size() - 1)
                                           .getFrom());
            } else {
                usr.setLastMessage("");
                usr.setLastMessageFrom("");
            }
        }
        return users;
    }

    @RequestMapping(value = "/search-users", method = RequestMethod.POST)
    public Set<CustomUser> searchUsers(@RequestBody MessageSender user) {
        return userService.searchUser(user.getLogin());
    }

    @RequestMapping(value = "/search-unsigned-users", method = RequestMethod.POST)
    public Set<CustomUserDto> searchUnsignedUsers(@RequestBody GroupDataDto data) {
        Set<CustomUserDto> users = new LinkedHashSet<>();
        users = userService.searchUsersToAddingToGroup(data.getUsers(), data.getGroupName());
        return users;
    }

    @RequestMapping(value = "/get-current-user", method = RequestMethod.POST)
    public CustomUser getCurrentUser() {
        return userService.getUserByLogin(getLoginCurrentUser());
    }

    @RequestMapping(value = "/save-network", method = RequestMethod.POST)
    public String saveNetwork(@RequestBody NetworkData network) {
        CustomUser customUsers = userService.getUserByLogin(getLoginCurrentUser());
        if (network.getNameOfTheNetwork()
                   .toLowerCase()
                   .trim()
                   .equals("facebook")) {
            customUsers.setFacebook(network.getUrlOfTheNetwork());
        } else if (network.getNameOfTheNetwork()
                          .toLowerCase()
                          .trim()
                          .equals("twitter")) {
            customUsers.setTwitter((network.getUrlOfTheNetwork()));
        } else customUsers.setInstagram(network.getUrlOfTheNetwork());
        userService.saveUser(customUsers);
        return "OK";
    }

    @RequestMapping(value = "/get-all-networks", method = RequestMethod.POST)
    public List<String> getAllNetworks() {
        CustomUser customUsers = userService.getUserByLogin(getLoginCurrentUser());
        return userService.getAllNetworks(customUsers);
    }

    @RequestMapping(value = "/visit-network", method = RequestMethod.POST)
    public String visitNetworks(@RequestBody NetworkData data) {
        return userService.getNetworkUrl(data.getOwner(), data.getNameOfTheNetwork());
    }

    private String getLoginCurrentUser() {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        return loggedInUser.getName();
    }

}
