package com.learning.greetingservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(classes = GreetingserviceApplication.class)
@AutoConfigureMockMvc
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

  @Test
  void shouldReturnHealthStatusUp() throws Exception{
    mockMvc.perform(MockMvcRequestBuilders.get("/actuator/health"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(org.hamcrest.Matchers.containsString("\"status\":\"UP\"")));
  }
}
