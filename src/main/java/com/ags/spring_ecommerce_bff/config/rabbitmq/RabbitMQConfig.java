package com.ags.spring_ecommerce_bff.config.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  private static final String DLQ_PREFIX = "dlq-";

  @Value("${spring.rabbitmq.exchanges.transfer-create}")
  private String TRANSFER_CREATE_EXCHANGE;

  @Value("${spring.rabbitmq.queues.transfer-create}")
  private String TRANSFER_CREATE_QUEUE;

  @Value("${spring.rabbitmq.bindings.transfer-create}")
  private String TRANSFER_CREATE_BINDING;

  @Bean
  public DirectExchange transferCreateExchange() {
    return new DirectExchange(TRANSFER_CREATE_EXCHANGE);
  }

  @Bean
  public Queue transferCreateQueue() {
    return QueueBuilder.durable(TRANSFER_CREATE_QUEUE)
        .withArgument("x-dead-letter-exchange", DLQ_PREFIX + TRANSFER_CREATE_EXCHANGE)
        .withArgument("x-dead-letter-routing-key", DLQ_PREFIX + TRANSFER_CREATE_BINDING)
        .build();
  }

  @Bean
  public Binding transferCreateBinding() {
    return BindingBuilder.bind(transferCreateQueue())
        .to(transferCreateExchange())
        .with(TRANSFER_CREATE_BINDING);
  }

  @Bean
  public DirectExchange transferCreateDlqExchange() {
    return new DirectExchange(DLQ_PREFIX + TRANSFER_CREATE_EXCHANGE);
  }

  @Bean
  public Queue transferCreateDlqQueue() {
    return new Queue(DLQ_PREFIX + TRANSFER_CREATE_QUEUE, true);
  }

  @Bean
  public Binding transferCreateDlqBinding() {
    return BindingBuilder.bind(transferCreateDlqQueue())
        .to(transferCreateDlqExchange())
        .with(DLQ_PREFIX + TRANSFER_CREATE_BINDING);
  }
}
