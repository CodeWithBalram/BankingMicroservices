package com.ultimate.auth.controller;

import com.ultimate.auth.dto.AuthRequest;
import com.ultimate.auth.dto.AuthResponse;
import com.ultimate.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    // 🔹 Use service layer (BEST PRACTICE)
    private final AuthService authService;

    /**
     * ✅ REGISTER API
     * - Takes username & password
     * - Calls service
     * - Returns JWT token
     */
    @PostMapping("/register")
    public AuthResponse register(@RequestBody AuthRequest request) {
        return authService.register(request);
    }

    /**
     * ✅ LOGIN API
     * - Verifies credentials
     * - Returns JWT token
     */
    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }
}