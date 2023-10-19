package com.iluwatar.messaging.service;

import com.iluwatar.messaging.exception.ConsumerNotFoundException;
import com.iluwatar.messaging.exception.OrderWithZeroItemException;
import com.iluwatar.messaging.exception.RestaurantNotFoundException;
import com.iluwatar.messaging.kafka.KafkaConsumer;
import com.iluwatar.messaging.model.MenuItemIdAndQuantity;
import com.iluwatar.messaging.model.Order;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class OrderServiceIntegrationTest {

  @Autowired OrderService orderService;

  @Autowired KafkaConsumer kafkaConsumer;

  @Test
  void shouldPlaceTheOrderAndSendOrderEvent() throws OrderWithZeroItemException, ConsumerNotFoundException, RestaurantNotFoundException, InterruptedException {

    Order order = orderService.createOrder(1001L, 2001L, testMenuItems());
    boolean messageConsumed = kafkaConsumer.getLatch().await(10, TimeUnit.SECONDS);

    assertNotNull(order);
    assertTrue(order.getOrderId() > 0);
    assertThat(kafkaConsumer.getPayload().contains("1001"));

  }

  @Test
  void shouldThrowConsumerNotFoundException() {
    Assertions.assertThrows(ConsumerNotFoundException.class, ()->orderService.createOrder(1, 2001, testMenuItems()));
  }

  @Test
  void shouldThrowRestaurantNotFoundException() {
    Assertions.assertThrows(RestaurantNotFoundException.class, ()->orderService.createOrder(1001L, 1, testMenuItems()));
  }

  @Test
  void shouldThrowOrderWithZeroItemException() {
    Assertions.assertThrows(OrderWithZeroItemException.class, ()->orderService.createOrder(1001L, 2001, null));
  }

  @NotNull private List<MenuItemIdAndQuantity> testMenuItems() {
    return List.of(new MenuItemIdAndQuantity("CAKE", 1));
  }

}
