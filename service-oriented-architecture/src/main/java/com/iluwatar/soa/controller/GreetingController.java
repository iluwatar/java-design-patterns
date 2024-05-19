package com.iluwatar.soa.controller;

import com.iluwatar.soa.services.interfaces.PersonalizedGreetingService;
import com.iluwatar.soa.services.registry.ServiceRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("home")
public class GreetingController {

  @GetMapping("/greeting")
  public String getGreeting() {
    PersonalizedGreetingService personalizedGreetingService =
        (PersonalizedGreetingService) ServiceRegistry.getService("personalizedGreetingService");
    return personalizedGreetingService.generateGreeting();
  }
}


