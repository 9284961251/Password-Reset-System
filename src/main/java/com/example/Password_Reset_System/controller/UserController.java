package com.example.Password_Reset_System.controller;

import com.example.Password_Reset_System.dto.LoginDto;
import com.example.Password_Reset_System.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
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
