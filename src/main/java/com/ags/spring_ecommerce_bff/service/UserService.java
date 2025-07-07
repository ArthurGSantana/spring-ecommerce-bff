package com.ags.spring_ecommerce_bff.service;

import com.ags.spring_ecommerce_bff.client.EcommerceServiceClient;
import com.ags.spring_ecommerce_bff.dto.UserDto;
import com.ags.spring_ecommerce_bff.exception.NotFoundException;
import com.ags.spring_ecommerce_bff.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final EcommerceServiceClient ecommerceServiceClient;

    public UserDto getUserById(UUID id) {
        log.info("Fetching user with ID {}", id);

        var user =
                userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));

        var userDto = objectMapper.convertValue(user, UserDto.class);
        userDto.setPassword(null);

        log.info("User with ID {} fetched successfully", id);

        return userDto;
    }

    public UserDto createUser(UserDto userDto) {
            return ecommerceServiceClient.createUser(userDto);
    }

    public List<UserDto> getAllUsers() {
        log.info("Fetching all users");

        var users = userRepository.findAll();

        return users.stream().map(user -> objectMapper.convertValue(user, UserDto.class))
                .peek(userDto -> userDto.setPassword(null))
                .toList();
    }
}
