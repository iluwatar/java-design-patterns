package com.iluwatar.eda.event;

import com.iluwatar.eda.model.User;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * {@link UserCreatedEventTest} tests and verifies {@link Event} behaviour.
 */
public class UserCreatedEventTest {

  /**
   * This unit test should correctly return the {@link Event} class type when calling the
   * {@link Event#getType() getType} method.
   */
  @Test
  public void testGetEventType() {
    User user = new User("iluwatar");
    UserCreatedEvent userCreatedEvent = new UserCreatedEvent(user);
    assertEquals(UserCreatedEvent.class, userCreatedEvent.getType());
  }
}
