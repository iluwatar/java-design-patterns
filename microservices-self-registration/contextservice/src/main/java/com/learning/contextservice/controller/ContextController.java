package com.learning.contextservice.controller;

import com.learning.contextservice.client.GreetingServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContextController {

  @Autowired
  private GreetingServiceClient greetingServiceClient;

  @Value("${user.region}")
  private String userRegion;

  @GetMapping("/context")
  public String getContext() {
    String greeting = greetingServiceClient.getGreeting();
    return "The Greeting Service says: "+greeting+" from "+userRegion;
  }
}
