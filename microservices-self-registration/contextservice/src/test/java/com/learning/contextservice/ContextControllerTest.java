package com.learning.contextservice;

import com.learning.contextservice.client.GreetingServiceClient;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(classes = ContextserviceApplication.class)
@AutoConfigureMockMvc
@Import(TestConfig.class)
class ContextControllerTest {

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

  @Test
  void shouldReturnContextServiceHealthStatusUp() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/actuator/health"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("\"status\":\"UP\"")));
  }
}
