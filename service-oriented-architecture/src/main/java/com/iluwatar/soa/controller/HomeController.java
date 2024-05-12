package com.iluwatar.soa.controller;

import com.iluwatar.soa.services.PersonalizedGreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("home")
public class HomeController {

  @Autowired
  PersonalizedGreetingService personalizedGreetingService;

  @GetMapping("/greeting")
  public String getGreeting() {
    return personalizedGreetingService.generateGreeting();
  }
}


