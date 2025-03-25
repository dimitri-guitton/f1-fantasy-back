package com.dg.f1fantasyback.controller;

import com.dg.f1fantasyback.model.dto.user.UserCreateDto;
import com.dg.f1fantasyback.security.JWTService;
import com.dg.f1fantasyback.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class SecurityController {
    private final JWTService jwtService;
    private final UserService userService;


    public SecurityController(JWTService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/token")
    public Map<String, String> getToken(Authentication authentication) {
        String token = jwtService.generateToken(authentication);
        return Map.of("token", token);
    }

    @PostMapping("/register")
    public Map<String, String> register(@Valid @RequestBody UserCreateDto userCreateDto) {
        String token = userService.register(userCreateDto);
        return Map.of("token", token);
    }
}
