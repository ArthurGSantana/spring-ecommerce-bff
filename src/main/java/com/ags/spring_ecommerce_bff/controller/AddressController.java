package com.ags.spring_ecommerce_bff.controller;

import com.ags.spring_ecommerce_bff.dto.request.AddressRequestDto;
import com.ags.spring_ecommerce_bff.dto.response.AddressResponseDto;
import com.ags.spring_ecommerce_bff.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
@Tag(name = "Address", description = "Address management operations")
public class AddressController {
  private final AddressService addressService;

  @GetMapping
  @Operation(
      summary = "Get all addresses by User ID",
      description = "Fetch a list of all address in the system")
  public ResponseEntity<List<AddressResponseDto>> getAllAddresses(@RequestParam UUID userId) {
    var addresses = addressService.getAddressesByUserId(userId);
    return ResponseEntity.ok(addresses);
  }

  @GetMapping("{id}")
  @Operation(summary = "Get address by ID", description = "Fetch a address by their unique ID")
  public ResponseEntity<AddressResponseDto> getAddressById(@PathVariable UUID id) {
    var address = addressService.getAddressById(id);
    return ResponseEntity.ok(address);
  }

  @PostMapping
  @Operation(summary = "Create a new address", description = "Register a new address in the system")
  public ResponseEntity<AddressResponseDto> createAddress(
      @Valid @RequestBody AddressRequestDto addressDto) {
    var createdAddress = addressService.createAddress(addressDto);
    return ResponseEntity.ok(createdAddress);
  }

  @PutMapping("{id}")
  @Operation(summary = "Update address by ID", description = "Update an existing address's details")
  public ResponseEntity<AddressResponseDto> updateAddress(
      @PathVariable UUID id, @RequestBody AddressRequestDto addressDto) {
    var updatedAddress = addressService.updateAddress(id, addressDto);
    return ResponseEntity.ok(updatedAddress);
  }

  @DeleteMapping("{id}")
  @Operation(summary = "Delete address by ID", description = "Remove a address from the system")
  public ResponseEntity<Void> deleteAddress(@PathVariable UUID id) {
    addressService.deleteAddressById(id);
    return ResponseEntity.noContent().build();
  }
}
