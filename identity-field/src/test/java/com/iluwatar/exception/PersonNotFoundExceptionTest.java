package com.iluwatar.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonNotFoundExceptionTest {

  @Test
  void testGetMessage() {
    // Create a new PersonNotFoundException object.
    PersonNotFoundException exception = new PersonNotFoundException(1L);

    // Assert that the message of the exception is correct.
    assertEquals("Person with ID 1 not found", exception.getMessage());
  }

  @Test
  void testGetId() {
    // Create a new PersonNotFoundException object.
    PersonNotFoundException exception = new PersonNotFoundException(1L);

    // Assert that the ID of the exception is correct.
    assertEquals(1L, exception.getId());
  }
}
