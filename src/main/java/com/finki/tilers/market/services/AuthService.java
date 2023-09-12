package com.finki.tilers.market.services;

import com.finki.tilers.market.model.dto.TokenDto;
import com.finki.tilers.market.model.dto.UserCredentials;
import com.finki.tilers.market.model.entity.ApplicationUser;
import com.finki.tilers.market.repositories.UserRepository;
import com.finki.tilers.market.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class AuthService {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public TokenDto login(UserCredentials credentials) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword());

        Authentication result = authenticationManager.authenticate(authentication);
        if (result.isAuthenticated()) {
            return TokenDto.builder()
                    .access(jwtUtils.generateToken(credentials.getEmail(), false))
                    .refresh(jwtUtils.generateToken(credentials.getEmail(), true))
                    .build();
        } else {
            throw new BadCredentialsException("Authentication failed.");
        }
    }
}
