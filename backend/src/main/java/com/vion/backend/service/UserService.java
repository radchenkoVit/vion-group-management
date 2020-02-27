package com.vion.backend.service;

import com.vion.backend.entity.User;
import com.vion.backend.exceptions.BrokenRequestException;
import com.vion.backend.exceptions.NotFoundEntityException;
import com.vion.backend.repository.UserRepository;
import com.vion.backend.web.contoller.user.model.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private ModelMapper mapper;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public UserDto findByEmailContains(String email) {
        if (email != null && email.isEmpty()) {
            throw new BrokenRequestException("Email is empty");
        }

        return userRepository
                .findByEmailContains(email)
                .map(user -> UserDto.of(user.getId(), user.getName(), user.getEmail()))
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
    public UserDto activateUser(String code) {
        User user = userRepository
                .findByActivationCode(code)
                .orElseThrow(() -> new NotFoundEntityException("Activation code not exist"));

        user.setActive(true);
        return UserDto.of(user.getName(), user.getEmail());
    }
}
