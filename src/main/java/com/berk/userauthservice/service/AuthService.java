package com.berk.userauthservice.service;

import com.berk.userauthservice.dto.AuthResponse;
import com.berk.userauthservice.entity.Role;
import com.berk.userauthservice.entity.User;
import com.berk.userauthservice.exception.InvalidCredentialsException;
import com.berk.userauthservice.exception.UsernameAlreadyExistsException;
import com.berk.userauthservice.repository.UserRepository;
import com.berk.userauthservice.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse register(String username, String rawPassword) {

        if(userRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);

        User user = new User(username, encodedPassword, Role.USER);

        userRepository.save(user);

        return new AuthResponse(user.getUsername());
    }

    public AuthResponse login(String username, String rawPassword) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new InvalidCredentialsException("User not found"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid password");
        }

        String token = jwtService.generateToken(user);

        return new AuthResponse(token);
    }
}
