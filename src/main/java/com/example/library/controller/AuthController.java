package com.example.library.controller;

import com.example.library.service.AuthService;
import com.example.library.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;

    public AuthController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        return userService.registerUser(username, password);
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        return authService.authenticateUser(username, password);
    }
}
