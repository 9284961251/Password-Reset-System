package com.example.Password_Reset_System.service;

public interface PasswordResetService {

    void requestOtp(String email);

    void confirmReset(String email, String otp, String newPassword);
}
