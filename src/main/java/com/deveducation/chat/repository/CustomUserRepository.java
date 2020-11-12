package com.deveducation.chat.repository;


import com.deveducation.chat.entity.CustomUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CustomUserRepository extends JpaRepository<CustomUser,Long> {


    CustomUser findByLogin(String login);

    boolean existsByLogin(String login);

    Set<CustomUser> findByLoginStartsWith(String login);






}
