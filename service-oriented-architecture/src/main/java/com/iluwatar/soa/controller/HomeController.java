package com.iluwatar.soa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

  @GetMapping("/services")
  public String getServicesPage() {
    return "services_view.html";
  }
}
