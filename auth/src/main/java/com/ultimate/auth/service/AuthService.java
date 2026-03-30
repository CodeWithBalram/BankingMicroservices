package com.ultimate.auth.service;

import com.ultimate.auth.dto.AuthRequest;
import com.ultimate.auth.dto.AuthResponse;
import com.ultimate.auth.entity.User;
import com.ultimate.auth.repository.UserRepository;
import com.ultimate.commonlib.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    // 🔹 Repository to interact with DB
    private final UserRepository userRepository;

    // 🔹 Used to encrypt & match passwords
    private final PasswordEncoder passwordEncoder;

    // 🔹 Used to generate JWT tokens
    private final JwtService jwtService;

    /**
     * ✅ REGISTER METHOD
     * - Creates new user
     * - Encrypts password
     * - Saves to DB
     * - Returns JWT token
     */
    public AuthResponse register(AuthRequest request) {

        // 1️⃣ Check if username already exists
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        // 2️⃣ Create new user object
        User user = User.builder()
                .username(request.getUsername())

                // 🔐 Always encode password before saving
                .password(passwordEncoder.encode(request.getPassword()))

                // 👤 Default role assigned
                .role("ROLE_USER")
                .build();

        // 3️⃣ Save user in database
        userRepository.save(user);

        // 4️⃣ Generate JWT token (with role)
        String token = jwtService.generateTokenWithRole(
                user.getUsername(),
                user.getRole()
        );

        // 5️⃣ Return token in response
        return new AuthResponse(token);
    }

    /**
     * ✅ LOGIN METHOD
     * - Verifies user credentials
     * - Matches password
     * - Returns JWT token
     */
    public AuthResponse login(AuthRequest request) {

        // 1️⃣ Fetch user from DB
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2️⃣ Match raw password with encoded password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        // 3️⃣ Generate JWT token (with role)
        String token = jwtService.generateTokenWithRole(
                user.getUsername(),
                user.getRole()
        );

        // 4️⃣ Return token
        return new AuthResponse(token);
    }
}