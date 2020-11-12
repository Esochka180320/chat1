package com.deveducation.chat.dto;

public class ChatMessageDto {

    private String message;
    private Integer newMessageCount;

    private String to;
    private String from;

    public ChatMessageDto(String message, Integer newMessageCount , String from ,String to) {
        this.message = message;
        this.newMessageCount = newMessageCount;
        this.from=from;
        this.to=to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getNewMessageCount() {
        return newMessageCount;
    }

    public void setNewMessageCount(Integer newMessageCount) {
        this.newMessageCount = newMessageCount;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
