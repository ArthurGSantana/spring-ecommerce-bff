package com.ags.spring_ecommerce_bff.service;

import com.ags.spring_ecommerce_bff.client.EcommerceServiceClient;
import com.ags.spring_ecommerce_bff.dto.request.AddressRequestDto;
import com.ags.spring_ecommerce_bff.dto.response.AddressResponseDto;
import com.ags.spring_ecommerce_bff.repository.AddressRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddressService {
  private final AddressRepository addressRepository;
  private final ModelMapper modelMapper;
  private final EcommerceServiceClient ecommerceServiceClient;

  public AddressResponseDto getAddressById(UUID addressId) {
    log.info("Fetching address with ID {}", addressId);

    var address =
        addressRepository
            .findById(addressId)
            .orElseThrow(() -> new RuntimeException("Address not found"));

    log.info("Address with ID {} fetched successfully", addressId);

    return modelMapper.map(address, AddressResponseDto.class);
  }

  public List<AddressResponseDto> getAllAddresses() {
    log.info("Fetching all addresses");

    var addresses = addressRepository.findAll();

    log.info("Fetched {} addresses successfully", addresses.size());

    return addresses.stream()
        .map(address -> modelMapper.map(address, AddressResponseDto.class))
        .toList();
  }

  public AddressResponseDto createAddress(AddressRequestDto addressDto) {
    log.info("Creating new address");

    var address = ecommerceServiceClient.createAddress(addressDto);

    log.info("Address created successfully with ID {}", address.getId());

    return address;
  }

  public AddressResponseDto updateAddress(UUID addressId, AddressRequestDto addressDto) {
    log.info("Updating address with ID {}", addressId);

    var existingAddress =
        addressRepository
            .findById(addressId)
            .orElseThrow(() -> new RuntimeException("Address not found"));

    var updatedAddress = ecommerceServiceClient.updateAddress(addressDto, addressId);

    log.info("Address with ID {} updated successfully", addressId);

    return updatedAddress;
  }

  public void deleteAddressById(UUID addressId) {
    log.info("Deleting address with ID {}", addressId);

    var existingAddress =
        addressRepository
            .findById(addressId)
            .orElseThrow(() -> new RuntimeException("Address not found"));

    ecommerceServiceClient.deleteAddressById(addressId);

    log.info("Address with ID {} deleted successfully", addressId);
  }
}
