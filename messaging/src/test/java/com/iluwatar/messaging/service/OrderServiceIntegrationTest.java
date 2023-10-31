/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.messaging.service;

import com.iluwatar.messaging.exception.ConsumerNotFoundException;
import com.iluwatar.messaging.exception.OrderWithZeroItemException;
import com.iluwatar.messaging.exception.RestaurantNotFoundException;
import com.iluwatar.messaging.kafka.KafkaConsumer;
import com.iluwatar.messaging.model.MenuItemIdAndQuantity;
import com.iluwatar.messaging.model.Order;
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
  private List<MenuItemIdAndQuantity> testMenuItems() {
    return List.of(new MenuItemIdAndQuantity("CAKE", 1));
  }

}
