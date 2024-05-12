package com.iluwatar.soa.services;

import org.springframework.stereotype.Service;

@Service
public class GreetingService {

  public String getGenericGreeting() {
    return "Hello, how are you today?";
  }

  public String getPersonalizedGreeting(String name) {
    return "Hello, " + name + "! How are you today?";
  }
}