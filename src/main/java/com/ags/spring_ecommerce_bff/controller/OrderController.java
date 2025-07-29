package com.ags.spring_ecommerce_bff.controller;

import com.ags.spring_ecommerce_bff.dto.response.OrderResponseDto;
import com.ags.spring_ecommerce_bff.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order", description = "Order management operations")
public class OrderController {
  private final OrderService orderService;

  @GetMapping
  @Operation(summary = "Get all orders", description = "Fetch a list of all order in the system")
  public ResponseEntity<List<OrderResponseDto>> getAllOrders() {
    var orders = orderService.getAllOrders();
    return ResponseEntity.ok(orders);
  }

  @GetMapping("{id}")
  @Operation(summary = "Get order by ID", description = "Fetch a order by their unique ID")
  public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable UUID id) {
    var order = orderService.getOrderById(id);
    return ResponseEntity.ok(order);
  }

  //  @PostMapping
  //  @Operation(summary = "Create a new order", description = "Register a new order in the system")
  //  public ResponseEntity<OrderResponseDto> createOrder(
  //      @Valid @RequestBody OrderRequestDto orderDto) {
  //    var createdOrder = orderService.createOrder(orderDto);
  //    return ResponseEntity.ok(createdOrder);
  //  }
  //
  //  @PutMapping("{id}")
  //  @Operation(summary = "Update order by ID", description = "Update an existing order's details")
  //  public ResponseEntity<OrderResponseDto> updateOrder(
  //      @PathVariable UUID id, @RequestBody OrderRequestDto orderDto) {
  //    var updatedOrder = orderService.updateOrder(id, orderDto);
  //    return ResponseEntity.ok(updatedOrder);
  //  }
  //
  //  @DeleteMapping("{id}")
  //  @Operation(summary = "Delete order by ID", description = "Remove a order from the system")
  //  public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
  //    orderService.deleteOrderById(id);
  //    return ResponseEntity.noContent().build();
  //  }
}
