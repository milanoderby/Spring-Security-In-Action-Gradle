package com.madcoder.ch16ex6.controller;

import com.madcoder.ch16ex6.service.NameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private NameService nameService;

    @GetMapping("/hello")
    public String hello() {
        return "Hello, " + nameService.getName();
    }
}