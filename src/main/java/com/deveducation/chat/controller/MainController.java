package com.deveducation.chat.controller;

import com.deveducation.chat.entity.ChatMessage;
import com.deveducation.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
public class MainController {
    @Autowired
    MessageService messageService;

    @RequestMapping("/sign")
    public ModelAndView sign() {
        return new ModelAndView("sign");
    }

    @RequestMapping("/game")
    public ModelAndView game() {
        return new ModelAndView("x0");
    }

    @RequestMapping("/cancel")
    public ModelAndView cancel() {
        return new ModelAndView("chat");
    }

    @RequestMapping("/")
    public ModelAndView showChat() {
        return new ModelAndView("chat");
    }

    @RequestMapping(value = "/get-сhat", method = RequestMethod.POST)
    public List<ChatMessage> showChat(@RequestParam("firstUser") String firstUsers,
                                      @RequestParam("secondUser") String secondUser) {

        List<ChatMessage> history = messageService.getPrivateMessage(firstUsers, secondUser);
        return history;
    }

}
