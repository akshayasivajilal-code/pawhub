package com.example.demo.controller;

import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.user;
import com.example.demo.Service.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public user register(@RequestBody user user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public user login(@RequestBody user user) {
        return userService.loginUser(
                user.getEmail(),
                user.getPassword()
        );
    }
}