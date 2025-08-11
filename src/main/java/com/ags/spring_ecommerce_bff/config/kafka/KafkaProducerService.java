package com.ags.spring_ecommerce_bff.config.kafka;

import com.ags.spring_ecommerce_bff.dto.request.OrderRequestDto;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {
  private final KafkaTemplate<String, Object> kafkaTemplate;

  private static final String ERROR_TOPIC = "Error sending message to topic: {}, error: {}";
  private static final String INFO_TOPIC = "Message sent to topic: {}, value: {}";

  @Value("${spring.kafka.topics.order-create}")
  private String orderCreateTopic;

  @Value("${spring.kafka.topics.order-update}")
  private String orderUpdateTopic;

  public void sendOrderCreateMessage(OrderRequestDto order) {
    CompletableFuture<SendResult<String, Object>> future =
        kafkaTemplate.send(orderCreateTopic, order);

    future.whenComplete(
        (result, ex) -> {
          if (ex != null) {
            log.error(ERROR_TOPIC, orderCreateTopic, ex.getMessage());
          } else {
            log.info(INFO_TOPIC, orderCreateTopic, result.getProducerRecord().value());
          }
        });
  }

  public void sendOrderUpdateMessage(OrderRequestDto order) {
    CompletableFuture<SendResult<String, Object>> future =
        kafkaTemplate.send(orderCreateTopic, order);

    future.whenComplete(
        (result, ex) -> {
          if (ex != null) {
            log.error(ERROR_TOPIC, orderUpdateTopic, ex.getMessage());
          } else {
            log.info(INFO_TOPIC, orderUpdateTopic, result.getProducerRecord().value());
          }
        });
  }
}
