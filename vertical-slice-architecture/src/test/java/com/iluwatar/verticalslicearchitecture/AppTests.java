package com.iluwatar.verticalslicearchitecture;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@AllArgsConstructor
class AppTests {

  private final ApplicationContext applicationContext;

  @Test
  void contextLoads() {
    assertNotNull(applicationContext);
  }

}
