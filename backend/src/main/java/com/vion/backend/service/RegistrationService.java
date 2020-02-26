package com.vion.backend.service;

import com.vion.backend.config.MailProperties;
import com.vion.backend.entity.Role;
import com.vion.backend.entity.User;
import com.vion.backend.repository.UserRepository;
import com.vion.backend.web.contoller.user.model.RegistrationDto;
import com.vion.backend.web.contoller.user.model.RegistrationRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class RegistrationService {
    private UserRepository userRepository;
    private ModelMapper mapper;
    private BCryptPasswordEncoder passwordEncoder;
    private MailProperties mailProperties;

    @Autowired
    public RegistrationService(UserRepository userRepository, ModelMapper mapper, BCryptPasswordEncoder passwordEncoder, MailProperties mailProperties) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.mailProperties = mailProperties;
    }

    @Transactional
    public RegistrationDto register(RegistrationRequest request) {
        User user = mapper.map(request, User.class);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ROLE_USER);
        user.setActivationCode(UUID.randomUUID().toString());
        user.setActive(false);
        user.setCreatedDate(LocalDate.now());
        userRepository.save(user);

        return RegistrationDto.builder()
                .email(user.getEmail())
                .activationCodeLink(mailProperties.generateActivationCode(user.getActivationCode()))
                .build();
    }
}
