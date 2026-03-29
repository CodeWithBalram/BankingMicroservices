package com.ultimate.auth.controller;


import com.ultimate.auth.dto.AuthRequest;
import com.ultimate.auth.dto.AuthResponse;
import com.ultimate.auth.repository.UserRepository;
import com.ultimate.commonlib.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController
{



    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {

        // 1️⃣ Fetch user from DB
        com.ultimate.auth.entity.User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2️⃣ Verify password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // 3️⃣ Generate JWT (include username + role)
        String token = jwtService.generateTokenWithRole(user.getUsername(), user.getRole());

        // 4️⃣ Return response
        return new AuthResponse(token);
    }
}

