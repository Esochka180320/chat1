package com.deveducation.chat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity(name = "Public_Group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String ownerLogin;


    @JsonIgnore
    @ManyToMany
    private List<CustomUser> users;


    @OneToMany(cascade = CascadeType.ALL)
    private List<ChatMessage> chatMessage;


    public Group() {
    }

    public Group(String name, String ownerLogin, List<CustomUser> users) {
        this.name = name;
        this.ownerLogin = ownerLogin;
        this.users = users;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }

    public List<CustomUser> getUsers() {
        return users;
    }

    public void setUsers(List<CustomUser> users) {
        this.users = users;
    }

    public List<ChatMessage> getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(List<ChatMessage> chatMessage) {
        this.chatMessage = chatMessage;
    }

    public void addToChats(ChatMessage msg) {
        this.chatMessage.add(msg);
    }
    public void addUserToGroup(CustomUser customUser) {
        this.users.add(customUser);
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;
        Group group = (Group) o;
        return Objects.equals(getName(), group.getName()) &&
                Objects.equals(getOwnerLogin(), group.getOwnerLogin()) &&
                Objects.equals(getUsers(), group.getUsers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getOwnerLogin(), getUsers());
    }

}
