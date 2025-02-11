package com.students.studentProfile.service;

import com.students.studentProfile.model.User;
import com.students.studentProfile.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    @Override
    public UserDetails loadUserByUsername(String username){
        User user= userRepository.findByUsername(username);
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();

    }

    public String createNewUser(User user){
        logger.info("inside service layer of create new user");
        userRepository.insertUser(user);
        return "user created";
    }
}
