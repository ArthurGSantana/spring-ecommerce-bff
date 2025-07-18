package com.ags.spring_ecommerce_bff.service;

import com.ags.spring_ecommerce_bff.client.EcommerceServiceClient;
import com.ags.spring_ecommerce_bff.dto.request.UserRequestDto;
import com.ags.spring_ecommerce_bff.dto.response.UserResponseDto;
import com.ags.spring_ecommerce_bff.exception.errors.NotFoundException;
import com.ags.spring_ecommerce_bff.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
  private final UserRepository userRepository;
  private final ModelMapper modelMapper;
  private final EcommerceServiceClient ecommerceServiceClient;

  public UserResponseDto getUserById(UUID id) {
    log.info("Fetching user with ID {}", id);

    var user =
        userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));

    var userDto = modelMapper.map(user, UserResponseDto.class);

    log.info("User with ID {} fetched successfully", id);

    return userDto;
  }

  public List<UserResponseDto> getAllUsers() {
    log.info("Fetching all users");

    var users = userRepository.findAll();

    return users.stream()
        .map(user -> modelMapper.map(user, UserResponseDto.class))
        //        .peek(userDto -> userDto.setPassword(null)) //peek is used for substituting values
        // in a stream
        .toList();
  }

  public UserResponseDto createUser(UserRequestDto userDto) {
    var createdUser = ecommerceServiceClient.createUser(userDto);

    log.info(
        "User created successfully with ID: {} for email: {}",
        createdUser.getId(),
        createdUser.getEmail());

    return modelMapper.map(createdUser, UserResponseDto.class);
  }

  public UserResponseDto updateUser(UUID id, UserRequestDto userDto) {
    var updatedUser = ecommerceServiceClient.updateUser(userDto, id);

    log.info(
        "User updated successfully with ID: {} for email: {}",
        updatedUser.getId(),
        updatedUser.getEmail());

    return modelMapper.map(updatedUser, UserResponseDto.class);
  }

  public void deleteUserById(UUID id) {
    ecommerceServiceClient.deleteUserById(id);
    log.info("User with ID {} deleted successfully", id);
  }
}
