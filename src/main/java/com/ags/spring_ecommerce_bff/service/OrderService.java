package com.ags.spring_ecommerce_bff.service;

import com.ags.spring_ecommerce_bff.config.kafka.KafkaProducerService;
import com.ags.spring_ecommerce_bff.config.rabbitmq.RabbitMQProducer;
import com.ags.spring_ecommerce_bff.dto.request.OrderRequestDto;
import com.ags.spring_ecommerce_bff.dto.response.OrderItemResponseDto;
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
  private final RabbitMQProducer rabbitMQProducer;

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

  public List<OrderResponseDto> getAllOrdersByUserId(UUID userId) {
    log.info("Fetching all orders by user ID {}", userId);

    var orders = orderRepository.findByUserId(userId);

    log.info("Fetched {} orders successfully", orders.size());

    return orders.stream()
        .map(
            order ->
                OrderResponseDto.builder()
                    .id(order.getId())
                    .shippingAddressId(order.getShippingAddress().getId())
                    .items(
                        order.getItems().stream()
                            .map(
                                item ->
                                    OrderItemResponseDto.builder()
                                        .productId(item.getProduct().getId())
                                        .quantity(item.getQuantity())
                                        .build())
                            .toList())
                    .build())
        .toList();
  }

  public void createOrder(OrderRequestDto orderDto) {
    log.info("Creating new order");

    kafkaProducerService.sendOrderCreateMessage(orderDto);
    rabbitMQProducer.transferCreateSendMessage(orderDto);
  }

  public void updateOrder(UUID orderId, OrderRequestDto orderDto) {
    log.info("Updating order for order ID {}", orderId);

    var order = orderRepository.existsById(orderId);

    if (!order) {
      throw new NotFoundException("Order not found");
    }

    kafkaProducerService.sendOrderCreateMessage(orderDto);

    log.info("Order status updated successfully for order ID {}", orderId);
  }
}
