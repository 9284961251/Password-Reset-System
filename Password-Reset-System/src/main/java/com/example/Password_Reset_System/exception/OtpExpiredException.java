package com.example.Password_Reset_System.exception;

public class OtpExpiredException extends RuntimeException {

    public OtpExpiredException() {
        super("Invalid or expired OTP");
    }
}
