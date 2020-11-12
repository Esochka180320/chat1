package com.deveducation.chat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
public class ChatMessage {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column( columnDefinition="TEXT" , name = "text_message")
    private String message;

    @Column( name = "whom")
    private String to;
    @Column( name = "fromWhichUser")
    private String from;


    @JsonIgnore
    @ManyToOne
    private CustomUser customUser;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;


    @Enumerated(EnumType.STRING)
    private MessageStatus messageStatus;


    @ManyToOne(cascade = CascadeType.ALL)
    Group group;

    public ChatMessage() {
    }


    public ChatMessage(String message,String to,CustomUser customUser,MessageStatus messageStatus) {
        this.message=message;
        this.to=to;
        this.customUser=customUser;
        this.messageStatus=messageStatus;
        this.date=new Date();
        this.from=customUser.getLogin();

    }

    public ChatMessage(String message, String to, CustomUser currentUser, MessageStatus messageStatus, Group group)
    {
        this.message=message;
        this.to=to;
        this.customUser=currentUser;
        this.messageStatus=messageStatus;
        this.group=group;
        this.date=new Date();
        this.from=customUser.getLogin();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public CustomUser getCustomUser() {
        return customUser;
    }

    public void setCustomUser(CustomUser customUser) {
        this.customUser = customUser;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }


    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ChatMessage)) return false;
        ChatMessage that = (ChatMessage) o;
        return Objects.equals(getMessage(), that.getMessage()) &&
                Objects.equals(getTo(), that.getTo()) &&
                Objects.equals(getFrom(), that.getFrom()) &&
                getMessageStatus() == that.getMessageStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMessage(), getTo(), getFrom(), getMessageStatus());
    }
}
