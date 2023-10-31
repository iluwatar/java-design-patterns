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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iluwatar.messaging.exception.ConsumerNotFoundException;
import com.iluwatar.messaging.exception.OrderWithZeroItemException;
import com.iluwatar.messaging.exception.RestaurantNotFoundException;
import com.iluwatar.messaging.kafka.KafkaProducer;
import com.iluwatar.messaging.model.MenuItemIdAndQuantity;
import com.iluwatar.messaging.model.Order;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class OrderService {

  private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

  private final Map<Long, Order> orderMap = new HashMap<>();

  private final Set<Long> consumers = new HashSet<>();
  private final Set<Long> restaurants = new HashSet<>();

  private final KafkaProducer kafkaProducer;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Value("${app.message.topic.default}")
  private String defaultTopic;

  public OrderService(KafkaProducer kafkaProducer) {
    this.kafkaProducer = kafkaProducer;
  }

  @PostConstruct
  void init(){
    consumers.add(1001L);
    restaurants.add(2001L);
  }

  public Order createOrder(long consumerId, long restaurantId, List<MenuItemIdAndQuantity> lineItems)
      throws ConsumerNotFoundException, RestaurantNotFoundException, OrderWithZeroItemException {

    if (!consumers.contains(consumerId)) {
      throw new ConsumerNotFoundException(consumerId);
    }

    if (!restaurants.contains(restaurantId)) {
      throw new RestaurantNotFoundException(restaurantId);
    }

    if (CollectionUtils.isEmpty(lineItems)) {
      throw new OrderWithZeroItemException();
    }

    Order receivedOrder = Order.builder().orderId(newOrderId())
        .menuItemIdAndQuantityList(lineItems)
        .restaurantId(restaurantId)
        .consumerId(consumerId)
        .build();

    try {
      placeOrder(receivedOrder);
    } catch (JsonProcessingException e) {
      LOGGER.error("order failed to send due to exception", e);
    }

    return receivedOrder;
  }

  private void placeOrder(Order receivedOrder) throws JsonProcessingException {
    this.orderMap.put(receivedOrder.getOrderId(), receivedOrder);
    String orderPayload = objectMapper.writeValueAsString(receivedOrder);
    this.kafkaProducer.send(defaultTopic, orderPayload);

  }

  private int newOrderId() {
    return orderMap.keySet().size() + 1;
  }

}
