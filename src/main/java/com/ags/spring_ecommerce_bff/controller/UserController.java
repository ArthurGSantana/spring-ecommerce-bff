package com.ags.spring_ecommerce_bff.controller;

import com.ags.spring_ecommerce_bff.dto.UserDto;
import com.ags.spring_ecommerce_bff.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("{id}")
    @Operation(summary = "Get user by ID", description = "Fetch a user by their unique ID")
    public ResponseEntity<UserDto> getUserById(@PathVariable UUID id) {
        var user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    @Operation(summary = "Create a new user", description = "Register a new user in the system")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        var createdUser = userService.createUser(userDto);
        return ResponseEntity.ok(createdUser);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update user by ID", description = "Update an existing user's details")
    public ResponseEntity<UserDto> updateUser(@PathVariable UUID id, @RequestBody UserDto userDto) {
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
