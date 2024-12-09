package com.iluwatar;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class OrderCreatedListenerTest {

  @InjectMocks
  private OrderCreatedListener orderCreatedListener;

  @MockBean
  private EventPublisher eventPublisher;

  @Test
  public void testHandleOrderCreatedEvent() {
    // Arrange
    String orderId = "order123";
    OrderCreatedEvent event = new OrderCreatedEvent(orderId);

    // Act
    orderCreatedListener.handleOrderCreatedEvent(event);

    // Assert
    verify(eventPublisher, times(1)).publishEvent(event);
  }
}
