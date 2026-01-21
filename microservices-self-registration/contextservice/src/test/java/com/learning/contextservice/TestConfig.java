package com.learning.contextservice;

import com.learning.contextservice.client.GreetingServiceClient;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

  @Bean
  public GreetingServiceClient greetingServiceClient() {
    GreetingServiceClient mockClient = Mockito.mock(GreetingServiceClient.class);
    Mockito.when(mockClient.getGreeting()).thenReturn("Mocked Hello");
    return mockClient;
  }
}
