package com.ultimate.commonlib.config;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtConfig config; // ye class me secret + expiration hoga

    // ✅ Generate signing key from secret
    private Key getKey() {
        return Keys.hmacShaKeyFor(config.getSecret().getBytes());
    }


    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + config.getAccessExpiration()))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // 🔹 Token with username + role


    public String generateTokenWithRole(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        return Jwts.builder()
                .setSubject(username)
                .addClaims(claims)  // role include
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + config.getAccessExpiration()))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ Extract username from token
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // ✅ Extract role from token
    public String extractRole(String token) {
        return (String) Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role");
    }

}