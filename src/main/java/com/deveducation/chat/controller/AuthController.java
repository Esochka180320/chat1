package com.deveducation.chat.controller;

import com.deveducation.chat.entity.CustomUser;
import com.deveducation.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class AuthController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/registration")
    ModelAndView registration(@RequestParam("log") String login,
                              @RequestParam("pass") String password) {


        ModelAndView modelAndView = new ModelAndView("sign");



        String passHash = passwordEncoder.encode(password);

        CustomUser customUser = new CustomUser(login,passHash);
        if (!userService.saveUser(customUser)) {
            modelAndView.addObject("message", "Error!! User with typed login exist!");
            return modelAndView;
        } else {
            /*securityService.autoLogin(email, password);*/

            modelAndView.addObject("message", "Sign in to continue");
            return new ModelAndView("sign");
        }
    }




}
