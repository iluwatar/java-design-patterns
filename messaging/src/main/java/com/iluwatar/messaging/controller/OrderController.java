package com.iluwatar.messaging.controller;

import com.iluwatar.messaging.exception.ConsumerNotFoundException;
import com.iluwatar.messaging.exception.OrderWithZeroItemException;
import com.iluwatar.messaging.exception.RestaurantNotFoundException;
import com.iluwatar.messaging.model.Order;
import com.iluwatar.messaging.model.OrderRequest;
import com.iluwatar.messaging.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

  private final OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @PostMapping("/place") public ResponseEntity<Order> postOrder(@RequestBody @NonNull OrderRequest orderRequest) {

    if (orderRequest.getConsumerId() == 0 || orderRequest.getRestaurantId() == 0) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    try {
      return new ResponseEntity<>(orderService.createOrder(orderRequest.getConsumerId(), orderRequest.getRestaurantId(), orderRequest.getMenuItemIdAndQuantityList()),
          HttpStatus.CREATED);
    } catch (ConsumerNotFoundException | RestaurantNotFoundException | OrderWithZeroItemException e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

}
