package com.vion.backend.controller.rest;

import com.vion.backend.service.UserService;
import com.vion.backend.web.contoller.user.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/find")
    public ResponseEntity<UserDto> findByEmail(@RequestParam String email) {
        UserDto user = userService.findByEmailContains(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(value = "/me") //for header info at vue pages
    public ResponseEntity<UserDto> findMe() {
        String email = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        UserDto user = userService.findByEmailContains(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
