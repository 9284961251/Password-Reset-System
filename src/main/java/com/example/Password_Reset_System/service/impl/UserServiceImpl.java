package com.example.Password_Reset_System.service.impl;

import com.example.Password_Reset_System.dto.LoginDto;
import com.example.Password_Reset_System.entity.User;
import com.example.Password_Reset_System.exception.AppException;
import com.example.Password_Reset_System.repository.UserRepository;
import com.example.Password_Reset_System.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(LoginDto dto) {
        Optional<User> existingUser = userRepository.findByEmail(dto.getEmail());
        if (existingUser.isPresent()) {
            throw new AppException("User already registered", HttpStatus.BAD_REQUEST);
        }

        // Create a new user
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword())); // HASH PASSWORD
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public void loginUser(LoginDto dto) {

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() ->
                        new AppException("Invalid Email", HttpStatus.UNAUTHORIZED)
                );

        if (!passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
            throw new AppException("Invalid Password", HttpStatus.UNAUTHORIZED);
        }

    }
}
