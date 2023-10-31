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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iluwatar.messaging.model.MenuItemIdAndQuantity;
import com.iluwatar.messaging.model.OrderRequest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.ServletContext;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
@EmbeddedKafka(
    topics = {"order-topic"},
    partitions = 2, brokerProperties = {
    "listeners=PLAINTEXT://localhost:9092",
    "auto.create.topics.enable=true"})
public class OrderControllerIntegrationTest {

  ObjectMapper objectMapper = new ObjectMapper();
  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldPlaceTheOrderAndReturn_200WhenOrderIsOk() throws Exception {

    OrderRequest orderRequest = new OrderRequest(1001L, 2001L, testMenuItems());

    RequestBuilder requestBuilder = new RequestBuilder() {
      @Override public MockHttpServletRequest buildRequest(ServletContext servletContext) {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setRequestURI("/order/place");
        mockHttpServletRequest.setMethod("POST");
        try {
          mockHttpServletRequest.setContent(objectMapper.writeValueAsString(orderRequest).getBytes(StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
          throw new RuntimeException(e);
        }
        mockHttpServletRequest.setContentType(APPLICATION_JSON_VALUE);
        return mockHttpServletRequest;
      }
    };

    ResultActions resultActions = this.mockMvc.perform(requestBuilder).andExpect(status().isCreated());
    Assertions.assertNotNull(resultActions);
  }

  @Test
  void shouldPlaceTheOrderAndReturn_400WhenOrderIsInvalid() throws Exception {
    OrderRequest orderRequest = new OrderRequest();
    orderRequest.setConsumerId(1001L);
    orderRequest.setMenuItemIdAndQuantityList(testMenuItems());

    RequestBuilder requestBuilder = new RequestBuilder() {
      @SneakyThrows @Override public MockHttpServletRequest buildRequest(ServletContext servletContext) {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setRequestURI("/order/place");
        mockHttpServletRequest.setMethod("POST");
        mockHttpServletRequest.setContent(objectMapper.writeValueAsString(orderRequest).getBytes(StandardCharsets.UTF_8));
        mockHttpServletRequest.setContentType(APPLICATION_JSON_VALUE);
        return mockHttpServletRequest;
      }
    };

    ResultActions resultActions = this.mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());
    Assertions.assertNotNull(resultActions);
  }

  @Test
  void shouldPlaceTheOrderAndReturn_500WhenOrderDoesNotHaveItems() throws Exception {
    OrderRequest orderRequest = new OrderRequest();
    orderRequest.setConsumerId(1001L);
    orderRequest.setRestaurantId(2001L);
    orderRequest.setMenuItemIdAndQuantityList(null);

    RequestBuilder requestBuilder = new RequestBuilder() {
      @Override public MockHttpServletRequest buildRequest(ServletContext servletContext) {
        MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
        mockHttpServletRequest.setRequestURI("/order/place");
        mockHttpServletRequest.setMethod("POST");
        try {
          mockHttpServletRequest.setContent(objectMapper.writeValueAsString(orderRequest).getBytes(StandardCharsets.UTF_8));
        } catch (JsonProcessingException e) {
          throw new RuntimeException(e);
        }
        mockHttpServletRequest.setContentType(APPLICATION_JSON_VALUE);
        return mockHttpServletRequest;
      }
    };

    ResultActions resultActions = this.mockMvc.perform(requestBuilder).andExpect(status().isInternalServerError());
    Assertions.assertNotNull(resultActions);
  }

  private List<MenuItemIdAndQuantity> testMenuItems() {
    return List.of(new MenuItemIdAndQuantity("CAKE", 1));
  }
}
