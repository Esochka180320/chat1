package com.deveducation.chat.entity;

import javax.persistence.*;

import java.util.*;

@Entity
public class CustomUser {

     @Id
     @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
     @Column(unique = true)
    private String login;
    private String avatar;
    private String password;
    @OneToMany(cascade = CascadeType.ALL)
    private List<ChatMessage> chatMessages = new LinkedList<>();
    private String facebook;
    private String twitter;
    private String instagram;


    private Integer newMessagesCount;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Group> group;

    public CustomUser() {
    }

    public CustomUser(String login, String avatar, List<ChatMessage> chatMessages,String password) {
        this.login = login;
        this.avatar = avatar;
        this.chatMessages = chatMessages;
        this.password=password;
    }


    public CustomUser(String login, String avatar, List<ChatMessage> chatMessages) {
        this.login = login;
        this.avatar = avatar;
        this.chatMessages = chatMessages;

    }




    public CustomUser(String login) {
        this.login = login;

    }

    public CustomUser(String login, String passHash) {
        this.login=login;
        this.password=passHash;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<ChatMessage> getChatMessages() {
        return chatMessages;
    }

    public void setChatMessages(List<ChatMessage> chatMessages) {
        this.chatMessages = chatMessages;
    }




    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public List<Group> getGroup() {
        return group;
    }

    public void setGroup(List<Group> group) {
        this.group = group;
    }


    public void initGroup(Group group) {
        this.group.add(group);
    }


    public Integer getNewMessagesCount() {
        return newMessagesCount;
    }

    public void setNewMessagesCount(Integer newMessagesCount) {
        this.newMessagesCount = newMessagesCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomUser that = (CustomUser) o;
        return Objects.equals(login, that.login) &&
                Objects.equals(avatar, that.avatar);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, avatar);
    }




}
