package com.learning.contextservice;

import com.learning.contextservice.client.GreetingServiceClient;
import com.learning.contextservice.controller.ContextController;
import com.netflix.discovery.converters.Auto;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(ContextController.class)
public class ContextControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private GreetingServiceClient greetingServiceClient;

  @Value("${user.region}")
  private String userRegion;

  @Test
  void shouldReturnContextGreeting() throws Exception{
    Mockito.when(greetingServiceClient.getGreeting()).thenReturn("Mocked Hello");

    mockMvc.perform(MockMvcRequestBuilders.get("/context")
        .accept(MediaType.TEXT_PLAIN))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("The Greeting Service says: Mocked Hello from Chennai, Tamil Nadu, India"));
  }
}
