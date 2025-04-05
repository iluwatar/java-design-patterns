package com.learning.contextservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="greetingservice")
public interface GreetingServiceClient {

  @GetMapping("/greeting")
  String getGreeting();
}
