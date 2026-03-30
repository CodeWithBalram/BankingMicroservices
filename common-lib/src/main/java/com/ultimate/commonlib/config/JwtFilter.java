package com.ultimate.commonlib.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.var;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

/**
 * JwtFilter intercepts every HTTP request (except /auth/**),
 * extracts and validates JWT token, and sets authentication in Spring Security context.
 */
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // 1️⃣ Skip JWT validation for authentication endpoints (login/register)
        if (request.getServletPath().startsWith("/auth/")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2️⃣ Get Authorization header
        final String authHeader = request.getHeader("Authorization");

        // 3️⃣ If header missing or doesn't start with "Bearer ", return 401 Unauthorized
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"Missing or invalid Authorization header\"}");
            return;
        }

        // 4️⃣ Extract token from header
        final String token = authHeader.substring(7); // Remove "Bearer " prefix

        try {
            // 5️⃣ Extract username and role from JWT
            String username = jwtService.extractUsername(token);
            String role = jwtService.extractRole(token);

            // 6️⃣ If username valid and authentication not yet set
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Set authority based on role
                var authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

                // Create authentication token and set in SecurityContext
                var authToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

        } catch (Exception e) {
            // 7️⃣ Invalid or expired token → clear context & return 401
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\":\"Invalid or expired token\"}");
            return;
        }

        // 8️⃣ Continue filter chain for valid requests
        filterChain.doFilter(request, response);
    }
}
