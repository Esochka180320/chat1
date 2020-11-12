package com.deveducation.chat.dto;

public class CustomUserDto  {

    private String avatar;
    private String login;
    private boolean isAdmin;
    private Integer countOfNewMessages;
    private String lastMessage;
    private String lastMessageFrom;

    public CustomUserDto() {
    }

    public CustomUserDto(String login, String avatar, boolean isAdmin) {
        this.avatar = avatar;
        this.login = login;
        this.isAdmin = isAdmin;
    }

    public CustomUserDto(String login, String avatar) {
        this.avatar = avatar;
        this.login = login;

    }

    public CustomUserDto(String avatar, String login, Integer countOfNewMessages) {
        this.avatar = avatar;
        this.login = login;
        this.countOfNewMessages = countOfNewMessages;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public Integer getCountOfNewMessages() {
        return countOfNewMessages;
    }

    public void setCountOfNewMessages(Integer countOfNewMessages) {
        this.countOfNewMessages = countOfNewMessages;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getLastMessageFrom() {
        return lastMessageFrom;
    }

    public void setLastMessageFrom(String lastMessageFrom) {
        this.lastMessageFrom = lastMessageFrom;
    }
}
