package com.finki.tilers.market.services;

import com.finki.tilers.market.model.dto.TokenDto;
import com.finki.tilers.market.model.dto.UserCredentials;
import com.finki.tilers.market.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public class AuthService {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    public TokenDto login(UserCredentials credentials) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());

        Authentication result = authenticationManager.authenticate(authentication);
        if (result.isAuthenticated()) {
            return TokenDto.builder()
                    .access(jwtUtils.generateToken(credentials.getUsername(), false))
                    .refresh(jwtUtils.generateToken(credentials.getUsername(), true))
                    .build();
        } else {
            throw new BadCredentialsException("Authentication failed.");
        }
    }
}
