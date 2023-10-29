package com.iluwatar.microservice.architecture;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
class OrderControllerTest {

    private MockMvc mockMvc;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    @Test
    void testCreateOrder() throws Exception {
        when(orderService.createOrder()).thenReturn("Order Created with ID: 1");

        mockMvc.perform(post("/api/orders/create"))
                .andExpect(status().isOk())
                .andExpect(content().string("Order Created with ID: 1"));
    }

    @Test
    void testFindOrderHistory() throws Exception {
        when(orderService.findOrderHistory()).thenReturn("Order History Details");

        mockMvc.perform(get("/api/orders/history"))
                .andExpect(status().isOk())
                .andExpect(content().string("Order History Details"));
    }

    @Test
    void testModifyOrder() throws Exception {
        when(orderService.modifyOrder(1L, "Updated Order Details")).thenReturn("Modified order with ID: 1");

        mockMvc.perform(put("/api/orders/modify/1")
                        .param("details", "Updated Order Details"))
                .andExpect(status().isOk())
                .andExpect(content().string("Modified order with ID: 1"));
    }

    @Test
    void testDeleteOrder() throws Exception {
        when(orderService.deleteOrder(1L)).thenReturn("Deleted order with ID: 1");

        mockMvc.perform(delete("/api/orders/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted order with ID: 1"));
    }
}
