package com.madcoder.ch15ex4rs.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(@AuthenticationPrincipal Jwt jwt) {
        String oauth2TokenClaims = jwt.getClaims().toString();
        return "Hello! " + oauth2TokenClaims;
    }

    @GetMapping("/user")
    public String readUserInfo() {
        return "User!";
    }
}