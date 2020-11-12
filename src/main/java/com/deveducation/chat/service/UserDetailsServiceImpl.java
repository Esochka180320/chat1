package com.deveducation.chat.service;

import com.deveducation.chat.entity.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        CustomUser customUser = userService.getUserByLogin(login);




        String log = customUser.getLogin();
        String password = customUser.getPassword();



        if (customUser.getLogin()==null)
            throw new UsernameNotFoundException(log + " not found");

        List<GrantedAuthority> roles =
                Arrays.asList(
                        new SimpleGrantedAuthority("USER"));


        return new User(customUser.getLogin(),
                customUser.getPassword(),roles);
    }
}