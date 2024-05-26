package com.madcoder.ch16ex6.service;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
public class NameService {

    @Secured("ROLE_ADMIN")
//    @RolesAllowed("ADMIN")
    public String getName() {
        return "Fantastico";
    }
}
