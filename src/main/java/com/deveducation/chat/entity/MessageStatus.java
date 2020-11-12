package com.deveducation.chat.entity;

public enum MessageStatus {
    privateMessage,publicMessage;

    @Override
    public String toString() {
        return name();
    }
}
