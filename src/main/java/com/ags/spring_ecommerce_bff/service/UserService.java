package com.ags.spring_ecommerce_bff.service;

import com.ags.spring_ecommerce_bff.dto.UserDto;
import com.ags.spring_ecommerce_bff.exception.NotFoundException;
import com.ags.spring_ecommerce_bff.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public UserDto getUserById(UUID id) {
        log.info("Fetching user with ID {}", id);

        var user =
                userRepository
                        .findById(id)
                        .orElseThrow(() -> new NotFoundException("User not found"));

        var userDto = objectMapper.convertValue(user, UserDto.class);
        userDto.setPassword(null);

        log.info("User with ID {} fetched successfully", id);

        return userDto;
    }
}
