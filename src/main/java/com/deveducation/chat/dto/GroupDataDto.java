package com.deveducation.chat.dto;

public class GroupDataDto {

    public String newName;
    private String groupName;
    private String users;//get via ',' ;

    private String lastMessage;
    private String lastMessageFrom;


    public GroupDataDto(String groupName, String lastMessage, String lastMessageFrom) {
        this.groupName = groupName;
        this.lastMessage = lastMessage;
        this.lastMessageFrom = lastMessageFrom;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }


    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
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
