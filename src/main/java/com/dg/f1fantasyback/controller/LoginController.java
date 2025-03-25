package com.dg.f1fantasyback.controller;

import com.dg.f1fantasyback.security.JWTService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    public JWTService jwtService;

    public LoginController(JWTService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/api/token")
    public String getToken(Authentication authentication) {

        return jwtService.generateToken(authentication);
    }

    @GetMapping("/api/token")
    public String getToken2(Authentication authentication) {

        return jwtService.generateToken(authentication);
    }
}
