package com.finki.tilers.market.controllers;

import com.finki.tilers.market.model.dto.RegisterDtoUser;
import com.finki.tilers.market.model.dto.TokenDto;
import com.finki.tilers.market.model.dto.UserCredentials;
import com.finki.tilers.market.model.entity.ApplicationUser;
import com.finki.tilers.market.security.JwtUtils;
import com.finki.tilers.market.services.AuthService;
import com.finki.tilers.market.services.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserCredentials credentials) {
        try {
            return ResponseEntity.ok(authService.login(credentials));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Username or password is not valid");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid RegisterDtoUser registerDto) {
            ApplicationUser registeredUser = userService.createOrUpdateUser(registerDto, true);
            return new ResponseEntity<>(TokenDto.builder()
                    .access(jwtUtils.generateToken(registerDto.getEmail(), false))
                    .refresh(jwtUtils.generateToken(registerDto.getEmail(), true))
                    .build(), HttpStatus.CREATED);
    }

}

