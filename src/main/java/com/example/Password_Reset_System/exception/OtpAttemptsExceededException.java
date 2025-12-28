package com.example.Password_Reset_System.exception;

public class OtpAttemptsExceededException extends RuntimeException {

    public OtpAttemptsExceededException() {
        super("Invalid or expired OTP");
    }
}
