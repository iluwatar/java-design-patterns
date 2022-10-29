package com.iluwatar.page.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
  private UserController userController;

  @Autowired
  MockMvc mockMvc;

  /**
   * Verify if view and model are directed properly
   */
  @Test
  void testGetUserPath () throws Exception {
        this.mockMvc.perform(get("/user")
                .param("name", "Lily")
                .param("email", "Lily@email.com"))
        .andExpect(status().isOk())
        .andExpect(model().attribute("name", "Lily"))
        .andExpect(model().attribute("email", "Lily@email.com"))
        .andReturn();
  }
}
