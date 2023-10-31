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
