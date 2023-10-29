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
class ConsumerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ConsumeService consumeService;

    @InjectMocks
    private ConsumerController consumerController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(consumerController).build();
    }

    @Test
    void testCreateConsumer() throws Exception {
        when(consumeService.createConsumer()).thenReturn("Consumer Created with ID: 1");

        mockMvc.perform(post("/api/consumers/create"))
                .andExpect(status().isOk())
                .andExpect(content().string("Consumer Created with ID: 1"));
    }

    @Test
    void testFindConsumer() throws Exception {
        when(consumeService.findConsumer(1L)).thenReturn("Sample Consumer Details for ID: 1");

        mockMvc.perform(get("/api/consumers/get/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Sample Consumer Details for ID: 1"));
    }

    @Test
    void testModifyConsumer() throws Exception {
        when(consumeService.modifyConsumer(1L, "Updated Details")).thenReturn("Modified consumer with ID: 1");

        mockMvc.perform(put("/api/consumers/modify/1")
                        .param("details", "Updated Details"))
                .andExpect(status().isOk())
                .andExpect(content().string("Modified consumer with ID: 1"));
    }

    @Test
    void testDeleteConsumer() throws Exception {
        when(consumeService.deleteConsumer(1L)).thenReturn("Deleted consumer with ID: 1");

        mockMvc.perform(delete("/api/consumers/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted consumer with ID: 1"));
    }
}
