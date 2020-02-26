package com.vion.backend.service.mail;

import com.vion.backend.web.contoller.user.model.RegistrationDto;
import com.vion.backend.web.contoller.user.model.UserDto;

import java.util.Map;

public interface MailService {
    void send(String emailTo, String subject, String template, Map<String, Object> model);
    void sendActivationEmail(RegistrationDto registrationDto);
    void sendWelcomeEmail(UserDto user);
}
