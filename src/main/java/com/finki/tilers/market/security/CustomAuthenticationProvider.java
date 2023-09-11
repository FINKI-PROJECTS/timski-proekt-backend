package com.finki.tilers.market.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    // Sample authentication method. In a real-world scenario, this should validate against your database or another authentication mechanism.
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        if ("user".equals(username) && "password".equals(password)) { // For demonstration purposes only
            return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
        } else {
            throw new BadCredentialsException("External system authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
