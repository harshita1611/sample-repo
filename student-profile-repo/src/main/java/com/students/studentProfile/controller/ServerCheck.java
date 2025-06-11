package com.students.studentProfile.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerCheck {

    @GetMapping
    public String checkServer(){
        return  "the server is running";
    }
}
