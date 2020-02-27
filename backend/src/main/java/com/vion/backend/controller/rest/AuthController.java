package com.vion.backend.controller.rest;

import com.vion.backend.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.vion.backend.config.UrlMapping.LOGOUT_ENDPOINT;

@RestController
public class AuthController {
    private final TokenService tokenService;

    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping(value = LOGOUT_ENDPOINT)
    public ResponseEntity logout(HttpServletRequest request) {
        tokenService.delete(request);
        return new ResponseEntity(null, HttpStatus.OK);
    }
}
