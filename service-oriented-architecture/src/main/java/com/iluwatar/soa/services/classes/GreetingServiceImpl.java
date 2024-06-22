package com.iluwatar.soa.services.classes;

import com.iluwatar.soa.services.interfaces.GreetingService;
import org.springframework.stereotype.Service;

@Service
public class GreetingServiceImpl implements GreetingService {

  public String getGenericGreeting() {
    return "Hello, how are you today?";
  }

}