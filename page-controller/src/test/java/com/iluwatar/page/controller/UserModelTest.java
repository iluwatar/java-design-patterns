package com.iluwatar.page.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class UserModelTest {
  /**
   * Verify if a user can set a name properly
   */
  @Test
  void testSetName() {
    UserModel model = new UserModel();
    model.setName("Lily");
    assertEquals("Lily", model.getName());
  }

  /**
   * Verify if a user can set an email properly
   */
  @Test
  void testSetEmail() {
    UserModel model = new UserModel();
    model.setEmail("Lily@email");
    assertEquals("Lily@email", model.getEmail());
  }
}
