package com.example.Password_Reset_System.service.impl;

import com.example.Password_Reset_System.entity.PasswordResetOtp;
import com.example.Password_Reset_System.entity.User;
import com.example.Password_Reset_System.exception.InvalidOtpException;
import com.example.Password_Reset_System.exception.OtpAttemptsExceededException;
import com.example.Password_Reset_System.exception.OtpExpiredException;
import com.example.Password_Reset_System.repository.PasswordResetOtpRepository;
import com.example.Password_Reset_System.repository.UserRepository;
import com.example.Password_Reset_System.service.EmailService;
import com.example.Password_Reset_System.service.PasswordResetService;
import com.example.Password_Reset_System.util.OtpGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PasswordResetServiceImpl implements PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetOtpRepository otpRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    @Override
    public void requestOtp(String email) {

        userRepository.findByEmail(email).ifPresent(user -> {

            String otp = OtpGenerator.generateOtp();

            PasswordResetOtp resetOtp = PasswordResetOtp.builder()
                    .email(email)
                    .otpHash(passwordEncoder.encode(otp))
                    .expiresAt(LocalDateTime.now().plusMinutes(5))
                    .build();

            otpRepository.save(resetOtp);

            // TODO: Replace with email sender
            emailService.sendOtpEmail(email, otp);
        });
    }

    @Override
    public void confirmReset(String email, String otp, String newPassword) {

        PasswordResetOtp resetOtp = otpRepository
                .findTopByEmailOrderByCreatedAtDesc(email)
                .orElseThrow(InvalidOtpException::new);

        if (resetOtp.isUsed()) {
            throw new InvalidOtpException();
        }

        if (resetOtp.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new OtpExpiredException();
        }

        if (resetOtp.getAttempts() >= 5) {
            throw new OtpAttemptsExceededException();
        }

        if (!passwordEncoder.matches(otp, resetOtp.getOtpHash())) {
            resetOtp.setAttempts(resetOtp.getAttempts() + 1);
            otpRepository.save(resetOtp);
            throw new InvalidOtpException();
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(InvalidOtpException::new);

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        resetOtp.setUsed(true);
        otpRepository.save(resetOtp);
    }
}
