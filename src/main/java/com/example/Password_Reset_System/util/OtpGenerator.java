package com.example.Password_Reset_System.util;

import java.security.SecureRandom;

public class OtpGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateOtp() {
        int otp = 100000 + RANDOM.nextInt(900000);
        return String.valueOf(otp);
    }
}
