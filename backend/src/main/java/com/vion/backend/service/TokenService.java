package com.vion.backend.service;

import com.vion.backend.entity.Token;
import com.vion.backend.repository.TokenRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

@Service
public class TokenService {
    @Getter
    @Value("${app.jwt.header.string}")
    private String jwtHeaderString;

    @Getter
    @Value("${app.jwt.token.prefix}")
    private String jwtTokenPrefix;

    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Transactional
    public void delete(HttpServletRequest request) {
        String token = resolveToken(request);
        if (token != null && tokenRepository.existsByToken(token)) {
            tokenRepository.deleteByToken(token);
        }
    }

    @Transactional(readOnly = true)
    public boolean isExist(HttpServletRequest request) {
        String token = resolveToken(request);
        if (token != null) {
            return tokenRepository.existsByToken(token);
        }
        return false;
    }

    @Transactional
    public void addToken(@NotNull String tokenString) {
        Token token = new Token();
        token.setToken(tokenString);
        tokenRepository.save(token);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(jwtHeaderString);
        if (bearerToken != null && bearerToken.startsWith(jwtTokenPrefix)) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
