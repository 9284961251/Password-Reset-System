package com.example.Password_Reset_System.service;


public interface EmailService {
    void sendOtpEmail(String to, String otp);
}
