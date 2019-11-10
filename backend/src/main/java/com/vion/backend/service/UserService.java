package com.vion.backend.service;

import com.vion.backend.entity.User;
import com.vion.backend.exceptions.BrokenRequestException;
import com.vion.backend.exceptions.NotFoundEntityException;
import com.vion.backend.repository.UserRepository;
import com.vion.backend.service.mail.MailManager;
import com.vion.backend.web.contoller.user.model.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private ModelMapper mapper;
    private MailManager mailManager;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper mapper, MailManager mailManager) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.mailManager = mailManager;
    }

    @Transactional(readOnly = true)
    public UserDto findByEmailContains(String email) {
        if (email != null && email.isEmpty()) {
            throw new BrokenRequestException("Email is empty");
        }

        return userRepository
                .findByEmailContains(email)
                .map(user -> mapper.map(user, UserDto.class))
                .orElseThrow(() -> new NotFoundEntityException("User Not Found"));
    }

    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        return userRepository
                .findAll()
                .stream()
                .map(u -> mapper.map(u, UserDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void activateUser(String code) {
        User user  = userRepository
                .findByActivationCode(code)
                .orElseThrow(() -> new NotFoundEntityException("Activation code not exist"));

        user.setActive(true);

        Map<String, Object> model = new HashMap<>();
        model.put("user", user);
        mailManager.send(user.getEmail(), "Welcome", "welcome-mail.ftl", model);
    }
}
