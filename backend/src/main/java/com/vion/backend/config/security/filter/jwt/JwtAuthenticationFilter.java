package com.vion.backend.config.security.filter.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vion.backend.service.TokenService;
import com.vion.backend.web.contoller.user.model.UserDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private JwtTokenProvider jwtTokenProvider;
    private TokenService tokenService;

    public JwtAuthenticationFilter(String url, AuthenticationManager authManager, JwtTokenProvider jwtTokenProvider, TokenService tokenService) {
        super(new AntPathRequestMatcher(url, "POST"));
        setAuthenticationManager(authManager);
        this.jwtTokenProvider = jwtTokenProvider;
        this.tokenService = tokenService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserDto creds = new ObjectMapper().readValue(request.getInputStream(), UserDto.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getEmail(),
                            creds.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication auth) {
        UserDetails user = (UserDetails) auth.getPrincipal();
        String role = user.getAuthorities().iterator().next().getAuthority();
        String token = jwtTokenProvider.generateToken(user.getUsername(), role);
        tokenService.addToken(token);

        response.addHeader(jwtTokenProvider.getJwtHeaderString(), (jwtTokenProvider.getJwtTokenPrefix() + token).trim());
        response.addHeader("roles", role);
    }
}
