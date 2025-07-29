package com.ags.spring_ecommerce_bff.config.kafka;

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

  @Value("${spring.kafka.topics.order-create}")
  private String orderCreateTopic;

  public void sendOrderCreateMessage(Object message) {
    CompletableFuture<SendResult<String, Object>> future =
        kafkaTemplate.send(orderCreateTopic, message);

    future.whenComplete(
        (result, ex) -> {
          if (ex != null) {
            log.error("Error sending message to topic {}: {}", orderCreateTopic, ex.getMessage());
          } else {
            log.info(
                "Message sent to topic {}: {}",
                orderCreateTopic,
                result.getProducerRecord().value());
          }
        });
  }
}
