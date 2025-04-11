package com.learning.greetingservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(GreetingsController.class)
class GreetingControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldReturnGreeting() throws Exception{
    mockMvc.perform(MockMvcRequestBuilders.get("/greeting")
            .accept(MediaType.TEXT_PLAIN))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("Hello"));
  }
}
