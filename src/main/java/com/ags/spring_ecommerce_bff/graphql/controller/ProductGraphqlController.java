package com.ags.spring_ecommerce_bff.graphql.controller;

import com.ags.spring_ecommerce_bff.dto.request.ProductRequestDto;
import com.ags.spring_ecommerce_bff.dto.response.ProductResponseDto;
import com.ags.spring_ecommerce_bff.service.ProductService;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ProductGraphqlController {
  private final ProductService productService;

  //    @SchemaMapping(typeName = "Query", field = "products")
  // com o SchemaMapping
  @QueryMapping // Como o nome do metodo é o mesmo que a query GraphQL, não é necessário especificar
  public ProductResponseDto getProductById(@Argument UUID id) {
    return productService.getProductById(id);
  }

  @QueryMapping
  public List<ProductResponseDto> getAllProducts() {
    return productService.getAllProducts();
  }

  @MutationMapping
  public ProductResponseDto createProduct(
      @Argument String name,
      @Argument String description,
      @Argument Double price,
      @Argument Integer stock) {
    var product =
        ProductRequestDto.builder()
            .name(name)
            .description(description)
            .price(BigDecimal.valueOf(price))
            .stock(stock)
            .build();
    return productService.createProduct(product);
  }

  @MutationMapping
  public ProductResponseDto updateProduct(
      @Argument UUID id,
      @Argument String name,
      @Argument String description,
      @Argument Double price,
      @Argument Integer stock) {
    var product =
        ProductRequestDto.builder()
            .id(id)
            .name(name)
            .description(description)
            .price(BigDecimal.valueOf(price))
            .stock(stock)
            .build();
    return productService.updateProduct(id, product);
  }

  @MutationMapping
  public Boolean deleteProduct(@Argument UUID id) {
    productService.deleteProduct(id);
    return true;
  }
}
