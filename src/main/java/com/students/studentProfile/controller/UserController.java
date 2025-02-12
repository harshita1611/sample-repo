package com.students.studentProfile.controller;


import com.students.studentProfile.utils.JwtTokenProvider;
import com.students.studentProfile.model.User;
import com.students.studentProfile.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("auth")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserService userService;


    public UserController(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.userService = userService;
    }


    @PostMapping("/register")
    public String createNewUser(@Valid @RequestBody User newUser) {
        logger.info("inside create new user");
        return userService.createNewUser(newUser);
    }
}
