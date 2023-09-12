package com.finki.tilers.market.security;

import com.finki.tilers.market.model.entity.ApplicationUser;
import com.finki.tilers.market.model.entity.Role;
import com.finki.tilers.market.services.AuthService;
import com.finki.tilers.market.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;
    // Sample authentication method. In a real-world scenario, this should validate against your database or another authentication mechanism.
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        ApplicationUser user = userService.checkIfUserPasswordMatches(email, password);


        if (user != null) { // For demonstration purposes only
            List<GrantedAuthority> authorities = user.getRole().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
                    .collect(Collectors.toList());
            return new UsernamePasswordAuthenticationToken(email, password, authorities);
        } else {
            throw new BadCredentialsException("External system authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
