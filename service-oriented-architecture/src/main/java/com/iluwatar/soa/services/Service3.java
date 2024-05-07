package com.iluwatar.soa.services;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Service3 {

  @GetMapping("/three/{name}")
  public String helloThree(@PathVariable("name") String name) {
    return "Hello, " + name + " from Service Three!";
  }
}
