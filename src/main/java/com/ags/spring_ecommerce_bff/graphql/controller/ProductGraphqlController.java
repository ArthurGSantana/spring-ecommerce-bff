package com.ags.spring_ecommerce_bff.graphql.controller;

import com.ags.spring_ecommerce_bff.dto.response.ProductResponseDto;
import com.ags.spring_ecommerce_bff.service.ProductService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
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
}
