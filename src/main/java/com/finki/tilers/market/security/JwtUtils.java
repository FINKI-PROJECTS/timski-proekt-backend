package com.finki.tilers.market.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    // TODO extract on properties
    private final String jwtSecret = "yourSecretKey";
    private final int jwtExpirationMs = 86400000;  // 1 day

    public String generateToken(String username, Boolean isRefresh) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + (jwtExpirationMs * (isRefresh ? 2 : 1))))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


    // You can also add methods to validate and parse JWT
}
