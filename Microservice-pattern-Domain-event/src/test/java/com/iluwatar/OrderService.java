package com.iluwatar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class OrderServiceTest {

  @Mock
  private EventPublisher eventPublisher;

  private OrderService orderService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this); // Initialize mocks
    orderService = new OrderService(new OrderCreatedListener(eventPublisher));
  }

  @Test
  void testCreateOrderPublishesEvent() {
    // Arrange
    String orderId = "123";

    // Act
    orderService.createOrder(orderId);

    // Assert
    verify(eventPublisher, times(1)).publishEvent(any(OrderCreatedEvent.class));
  }
}
