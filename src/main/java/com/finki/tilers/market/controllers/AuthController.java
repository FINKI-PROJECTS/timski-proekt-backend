package com.finki.tilers.market.controllers;

import com.finki.tilers.market.model.dto.UserCredentials;
import com.finki.tilers.market.security.JwtUtils;
import com.finki.tilers.market.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserCredentials credentials) {
        try {
            return ResponseEntity.ok(authService.login(credentials));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Username or password is not valid");
        }
    }
}

