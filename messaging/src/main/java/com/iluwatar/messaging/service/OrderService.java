package com.iluwatar.messaging.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iluwatar.messaging.exception.ConsumerNotFoundException;
import com.iluwatar.messaging.exception.OrderWithZeroItemException;
import com.iluwatar.messaging.exception.RestaurantNotFoundException;
import com.iluwatar.messaging.kafka.KafkaProducer;
import com.iluwatar.messaging.model.MenuItemIdAndQuantity;
import com.iluwatar.messaging.model.Order;
import jakarta.annotation.PostConstruct;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
