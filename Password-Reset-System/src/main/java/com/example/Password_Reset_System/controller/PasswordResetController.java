package com.example.Password_Reset_System.controller;

import com.example.Password_Reset_System.dto.PasswordResetConfirmDto;
import com.example.Password_Reset_System.dto.PasswordResetRequestDto;
import com.example.Password_Reset_System.service.PasswordResetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/password-reset")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/request")
    public ResponseEntity<?> requestOtp(
            @Valid @RequestBody PasswordResetRequestDto dto) {

        passwordResetService.requestOtp(dto.getEmail());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmReset(
            @Valid @RequestBody PasswordResetConfirmDto dto) {

        passwordResetService.confirmReset(
                dto.getEmail(),
                dto.getOtp(),
                dto.getNewPassword()
        );
        return ResponseEntity.ok().build();
    }
}
