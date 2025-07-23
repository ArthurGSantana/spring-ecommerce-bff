package com.ags.spring_ecommerce_bff.service;

import com.ags.spring_ecommerce_bff.dto.request.ProductRequestDto;
import com.ags.spring_ecommerce_bff.dto.response.ProductResponseDto;
import com.ags.spring_ecommerce_bff.exception.errors.NotFoundException;
import com.ags.spring_ecommerce_bff.grpc.ProductGrpcClient;
import com.ags.spring_ecommerce_bff.repository.ProductRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
  private final ProductRepository productRepository;
  private final ModelMapper modelMapper;
  private final ProductGrpcClient productGrpcClient;

  public ProductResponseDto getProductById(UUID productId) {
    log.info("Fetching product with ID {}", productId);

    var product =
        productRepository
            .findById(productId)
            .orElseThrow(() -> new NotFoundException("Product not found"));

    var productDto = modelMapper.map(product, ProductResponseDto.class);

    log.info("Product with ID {} fetched successfully", productId);

    return productDto;
  }

  public List<ProductResponseDto> getAllProducts() {
    log.info("Fetching all products");

    var products = productRepository.findAll();

    return products.stream()
        .map(product -> modelMapper.map(product, ProductResponseDto.class))
        .toList();
  }

  public ProductResponseDto createProduct(ProductRequestDto productDto) {
    log.info("Creating product with name {}", productDto.getName());

    var savedProduct = productGrpcClient.createProduct(productDto);

    log.info("Product created successfully with ID: {}", savedProduct.getId());

    return modelMapper.map(savedProduct, ProductResponseDto.class);
  }

  public ProductResponseDto updateProduct(UUID productId, ProductRequestDto productDto) {
    log.info("Updating product with ID {}", productId);

    var updatedProduct = productGrpcClient.updateProduct(productDto);

    log.info("Product with ID {} updated successfully", productId);

    return modelMapper.map(updatedProduct, ProductResponseDto.class);
  }

  public void deleteProduct(UUID productId) {
    log.info("Deleting product with ID {}", productId);

    productGrpcClient.deleteProduct(productId);

    log.info("Product with ID {} deleted successfully", productId);
  }
}
