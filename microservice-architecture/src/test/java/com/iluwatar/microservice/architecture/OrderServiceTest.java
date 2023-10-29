package com.iluwatar.microservice.architecture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private Order order;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        // Initialize mocks and inject them
    }

    @Test
    void testCreateOrder() {
        when(order.create()).thenReturn("Order Created with ID: 1");
        String result = orderService.createOrder();
        assertEquals("Order Created with ID: 1", result);
    }

    @Test
    void testFindOrderHistory() {
        when(order.history()).thenReturn("Order History Details");
        String result = orderService.findOrderHistory();
        assertEquals("Order History Details", result);
    }

    @Test
    void testModifyOrder() {
        when(order.modify(1L, "Updated Order Details")).thenReturn("Modified order with ID: 1");
        String result = orderService.modifyOrder(1L, "Updated Order Details");
        assertEquals("Modified order with ID: 1", result);
    }

    @Test
    void testDeleteOrder() {
        when(order.delete(1L)).thenReturn("Deleted order with ID: 1");
        String result = orderService.deleteOrder(1L);
        assertEquals("Deleted order with ID: 1", result);
    }
}
