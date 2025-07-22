package com.ags.spring_ecommerce_bff.grpc;

import com.ags.spring_ecommerce_bff.dto.request.ProductRequestDto;
import com.ags.spring_ecommerce_bff.dto.response.ProductResponseDto;
import com.ags.spring_ecommerce_bff.exception.errors.GrpcRequestException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductGrpcClient {
  @GrpcClient("product-service")
  private ProductServiceGrpc.ProductServiceBlockingStub productServiceStub;

  private final ModelMapper modelMapper;

  public ProductResponseDto createProduct(ProductRequestDto product) throws GrpcRequestException {
    try {
      var productRequest =
          CreateProductRequest.newBuilder()
              .setName(product.getName())
              .setDescription(product.getDescription())
              .setPrice(product.getPrice().doubleValue())
              .setStock(product.getStock())
              .build();
      var response = productServiceStub.createProduct(productRequest);
      return modelMapper.map(response, ProductResponseDto.class);
    } catch (Exception e) {
      throw new GrpcRequestException("Failed to create product: " + e.getMessage(), e);
    }
  }

  public ProductResponseDto updateProduct(ProductRequestDto product) throws GrpcRequestException {
    try {
      var productRequest =
          UpdateProductRequest.newBuilder()
              .setId(product.getId().toString())
              .setName(product.getName())
              .setDescription(product.getDescription())
              .setPrice(product.getPrice().doubleValue())
              .setStock(product.getStock())
              .build();
      var response = productServiceStub.updateProduct(productRequest);
      return modelMapper.map(response, ProductResponseDto.class);
    } catch (Exception e) {
      throw new GrpcRequestException("Failed to update product: " + e.getMessage(), e);
    }
  }

  public void deleteProduct(UUID productId) throws GrpcRequestException {
    try {
      var request = DeleteProductRequest.newBuilder().setId(productId.toString()).build();
      productServiceStub.deleteProduct(request);
    } catch (Exception e) {
      throw new GrpcRequestException("Failed to delete product: " + e.getMessage(), e);
    }
  }
}
