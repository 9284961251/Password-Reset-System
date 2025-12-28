package com.example.Password_Reset_System.service;

import com.example.Password_Reset_System.dto.LoginDto;


public interface UserService {
    void registerUser(LoginDto dto);
    void loginUser(LoginDto dto);
}
