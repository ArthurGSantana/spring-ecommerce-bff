package com.ags.spring_ecommerce_bff.controller;

import com.ags.spring_ecommerce_bff.dto.request.UserRequestDto;
import com.ags.spring_ecommerce_bff.dto.response.UserResponseDto;
import com.ags.spring_ecommerce_bff.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "User", description = "User management operations")
public class UserController {
  private final UserService userService;

  @GetMapping
  @Operation(summary = "Get all users", description = "Fetch a list of all users in the system")
  public ResponseEntity<List<UserResponseDto>> getAllUsers() {
    var users = userService.getAllUsers();
    return ResponseEntity.ok(users);
  }

  @GetMapping("{id}")
  @Operation(summary = "Get user by ID", description = "Fetch a user by their unique ID")
  public ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID id) {
    var user = userService.getUserById(id);
    return ResponseEntity.ok(user);
  }

  @PostMapping
  @Operation(summary = "Create a new user", description = "Register a new user in the system")
  public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserRequestDto userDto) {
    var createdUser = userService.createUser(userDto);
    return ResponseEntity.ok(createdUser);
  }

  @PutMapping("{id}")
  @Operation(summary = "Update user by ID", description = "Update an existing user's details")
  public ResponseEntity<UserResponseDto> updateUser(
      @PathVariable UUID id, @RequestBody UserRequestDto userDto) {
    var updatedUser = userService.updateUser(id, userDto);
    return ResponseEntity.ok(updatedUser);
  }

  @DeleteMapping("{id}")
  @Operation(summary = "Delete user by ID", description = "Remove a user from the system")
  public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
    userService.deleteUserById(id);
    return ResponseEntity.noContent().build();
  }
}
