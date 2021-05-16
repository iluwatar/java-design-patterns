package com.iluwatar.valet.key;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * User Test
 */
class UserTest {
  Application application = new Application();
  Resource resource = new Resource();
  User user = new User(null, application, resource);

  @Test
  void testRequest_valid() {
    boolean request = true;
    int target = 1;
    assertTrue(user.requestResource(request, target));
  }

  @Test
  void testRequest_invalid_1(){
    boolean request = false;
    int target = 1;
    assertFalse(user.requestResource(request, target));
  }

  @Test
  void testRequest_invalid_2(){
    boolean request = true;
    int target = 2;
    assertFalse(user.requestResource(request, target));
  }

  @Test
  void testRequest_invalid_3() {
    boolean request = false;
    int target = 2;
    assertFalse(user.requestResource(request, target));

    request = true;
    target = 1;
    assertTrue(user.requestResource(request, target));
  }

}
