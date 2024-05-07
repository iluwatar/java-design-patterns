package com.iluwatar.soa.services;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Service2 {

  @GetMapping("/two")
  public String helloTwo() {
    return "Hello from Service Two!";
  }
}
