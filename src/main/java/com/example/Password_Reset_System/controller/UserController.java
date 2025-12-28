package com.example.Password_Reset_System.controller;

import com.example.Password_Reset_System.dto.LoginDto;
import com.example.Password_Reset_System.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginDto dto){
        userService.loginUser(dto);
        return ResponseEntity.ok().build();

    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody LoginDto dto){
        System.out.println("hey");
        userService.registerUser(dto);
        return ResponseEntity.ok().build();
    }
}
