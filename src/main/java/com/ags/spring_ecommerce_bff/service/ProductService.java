package com.ags.spring_ecommerce_bff.service;

import com.ags.spring_ecommerce_bff.dto.response.ProductResponseDto;
import com.ags.spring_ecommerce_bff.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
  private final ProductRepository productRepository;

  public ProductResponseDto getProductById(String productId) {
    log.info("Fetching product with ID: {}", productId);
    return productRepository.findById(UUID.fromString(productId))
        .map(ProductResponseDto::fromEntity)
        .orElseThrow(() -> new RuntimeException("Product not found with ID: " + productId));
  }
}
