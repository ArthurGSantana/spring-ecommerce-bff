package com.ags.spring_ecommerce_bff.service;

import com.ags.spring_ecommerce_bff.dto.request.ProductRequestDto;
import com.ags.spring_ecommerce_bff.dto.request.ProductRequestFilterDto;
import com.ags.spring_ecommerce_bff.dto.response.ProductResponseDto;
import com.ags.spring_ecommerce_bff.dto.response.ProductResponseFilterDto;
import com.ags.spring_ecommerce_bff.exception.errors.NotFoundException;
import com.ags.spring_ecommerce_bff.grpc.ProductGrpcClient;
import com.ags.spring_ecommerce_bff.repository.ProductRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

  public ProductResponseFilterDto<ProductResponseDto> getAllProductsByFilter(
      ProductRequestFilterDto filter) {
    log.info("Buscando produtos com filtro: sku={}, name={}", filter.getSku(), filter.getName());

    var sort = Sort.unsorted();
    if (filter.getSortBy() != null && filter.getSortOrder() != null) {
      sort =
          Sort.by(
              filter.getSortOrder().equalsIgnoreCase("desc")
                  ? Sort.Direction.DESC
                  : Sort.Direction.ASC,
              filter.getSortBy());
    }

    Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize(), sort);

    var productsPage =
        productRepository.findBySkuContainingIgnoreCaseAndNameContainingIgnoreCase(
            filter.getSku() == null ? "" : filter.getSku(),
            filter.getName() == null ? "" : filter.getName(),
            pageable);

    return ProductResponseFilterDto.<ProductResponseDto>builder()
        .content(
            productsPage.getContent().stream()
                .map(product -> modelMapper.map(product, ProductResponseDto.class))
                .toList())
        .totalElements(productsPage.getTotalElements())
        .totalPages(productsPage.getTotalPages())
        .build();
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
