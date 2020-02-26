package com.vion.backend.controller.rest;

import com.vion.backend.service.UserService;
import com.vion.backend.service.mail.MailService;
import com.vion.backend.web.contoller.user.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/activate")
public class ActivationController {
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;

    @GetMapping
    @RequestMapping(value = "/{code}")
    public String activate(@PathVariable String code) {
        UserDto userDto = userService.activateUser(code);
        mailService.sendWelcomeEmail(userDto);
        return "Activated";//TODO: how make it looks more better that it looks right now -> just message of Get request
    }
}
