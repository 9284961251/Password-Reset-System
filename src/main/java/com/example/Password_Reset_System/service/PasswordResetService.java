package com.example.Password_Reset_System.service;

import com.example.Password_Reset_System.dto.LoginDto;

public interface PasswordResetService {

    void requestOtp(String email);
    void confirmReset(String email, String otp, String newPassword);
}
