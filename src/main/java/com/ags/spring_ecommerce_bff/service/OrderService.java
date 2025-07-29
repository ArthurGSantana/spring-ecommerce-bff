package com.ags.spring_ecommerce_bff.service;

import com.ags.spring_ecommerce_bff.config.kafka.KafkaProducerService;
import com.ags.spring_ecommerce_bff.dto.request.OrderRequestDto;
import com.ags.spring_ecommerce_bff.dto.response.OrderResponseDto;
import com.ags.spring_ecommerce_bff.exception.errors.NotFoundException;
import com.ags.spring_ecommerce_bff.repository.OrderRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;
  private final ModelMapper modelMapper;
  private final KafkaProducerService kafkaProducerService;

  public OrderResponseDto getOrderById(UUID orderId) {
    log.info("Fetching order with ID {}", orderId);

    var order =
        orderRepository
            .findById(orderId)
            .orElseThrow(() -> new NotFoundException("Order not found"));

    var orderDto = modelMapper.map(order, OrderResponseDto.class);

    log.info("Order with ID {} fetched successfully", orderId);

    return orderDto;
  }

  public List<OrderResponseDto> getAllOrders() {
    log.info("Fetching all orders");

    var orders = orderRepository.findAll();

    log.info("Fetched {} orders successfully", orders.size());

    return orders.stream().map(order -> modelMapper.map(order, OrderResponseDto.class)).toList();
  }

  public void createOrder(OrderRequestDto orderDto) {
    log.info("Creating new order");

    kafkaProducerService.sendOrderCreateMessage(orderDto);
  }
}
