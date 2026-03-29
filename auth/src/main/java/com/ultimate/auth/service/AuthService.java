package com.ultimate.auth.service;

import com.ultimate.auth.dto.AuthRequest;
import com.ultimate.auth.dto.AuthResponse;
import com.ultimate.auth.entity.User;
import com.ultimate.auth.repository.UserRepository;
import com.ultimate.commonlib.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    // ✅ Register user and return JWT with role
    public AuthResponse register(AuthRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ROLE_USER") // default role
                .build();
         userRepository.save(user);
        // Use role-based JWT
        String token = jwtService.generateTokenWithRole(user.getUsername(), user.getRole());
        return new AuthResponse(token);
    }

    // ✅ Login user and return JWT with role
    public AuthResponse login(AuthRequest request) {
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());

        if (!userOpt.isPresent()) {  // isEmpty() ki jagah isPresent() ka ! use karo
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(token);
    }
}