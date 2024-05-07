package com.iluwatar.soa.services;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

  @RestController
  public class Service1 {

    @GetMapping("/one")
    public String helloOne() {
      return "Hello from Service One!";
    }
  }
