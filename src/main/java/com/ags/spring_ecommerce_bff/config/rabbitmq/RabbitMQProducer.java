package com.ags.spring_ecommerce_bff.config.rabbitmq;

import com.ags.spring_ecommerce_bff.dto.request.OrderRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RabbitMQProducer {
  @Value("${spring.rabbitmq.queues.transfer-create}")
  private String TRANSFER_CREATE_QUEUE;

  @Value("${spring.rabbitmq.bindings.transfer-create}")
  private String TRANSFER_CREATE_BINDING;

  private final RabbitTemplate rabbitTemplate;

  public void transferCreateSendMessage(OrderRequestDto message) {
    System.out.println("Produtor: Enviando mensagem -> '" + message + "'");
    rabbitTemplate.convertAndSend(TRANSFER_CREATE_QUEUE, TRANSFER_CREATE_BINDING, message);
  }
}
