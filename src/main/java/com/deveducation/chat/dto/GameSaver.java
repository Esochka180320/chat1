package com.deveducation.chat.dto;

import lombok.Getter;

@Getter
public class GameSaver {

    private String to;
    private String from;
    private Command command;
    private String[][] gameState;
    private String winner;

    private String turn;

//    public String getTurn() {
//        return turn;
//    }
//
//    public void setTurn(String turn) {
//        this.turn = turn;
//    }
//
//
//
//
//    public String getTo() {
//        return to;
//    }
//
//    public void setTo(String to) {
//        this.to = to;
//    }
//
//    public String getFrom() {
//        return from;
//    }
//
//    public void setFrom(String from) {
//        this.from = from;
//    }
}
