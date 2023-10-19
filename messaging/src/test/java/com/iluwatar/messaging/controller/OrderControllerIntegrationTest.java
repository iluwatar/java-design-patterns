package com.iluwatar.messaging.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iluwatar.messaging.model.MenuItemIdAndQuantity;
import com.iluwatar.messaging.model.OrderRequest;
import jakarta.servlet.ServletContext;
import org.jetbrains.annotations.NotNull;
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

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"listeners=PLAINTEXT://localhost:9092", "port=9092"})
public class OrderControllerIntegrationTest {

  ObjectMapper objectMapper = new ObjectMapper();
  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldPlaceTheOrderAndReturn_200WhenOrderIsOk() throws Exception {

    OrderRequest orderRequest = new OrderRequest();
    orderRequest.setConsumerId(1001L);
    orderRequest.setRestaurantId(2001L);
    orderRequest.setMenuItemIdAndQuantityList(testMenuItems());

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
  }

  @Test
  void shouldPlaceTheOrderAndReturn_400WhenOrderIsInvalid() throws Exception {
    OrderRequest orderRequest = new OrderRequest();
    orderRequest.setConsumerId(1001L);
    orderRequest.setMenuItemIdAndQuantityList(testMenuItems());

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

    ResultActions resultActions = this.mockMvc.perform(requestBuilder).andExpect(status().isBadRequest());
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
  }

  @NotNull private List<MenuItemIdAndQuantity> testMenuItems() {
    return List.of(new MenuItemIdAndQuantity("CAKE", 1));
  }
}
