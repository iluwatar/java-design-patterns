package com.iluwatar.proactor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.NotActiveException;

public class ConcreteCompletionHandlerTest {

  @Test
  void testHandleEvent1(){
    Handle handle = new Handle("1");
    ConcreteCompletionHandler c = new ConcreteCompletionHandler("2");
    Assertions.assertThrows(NotActiveException.class, ()-> c.handleEvent(handle));
  }

  @Test
  void testHandleEvent2(){
    Handle handle = new Handle("1");
    ConcreteCompletionHandler c = new ConcreteCompletionHandler("1");
    assertDoesNotThrow(()-> c.handleEvent(handle));
  }

  @Test
  void testHandleEvent3() throws NotActiveException {
    Handle handle = new Handle("1");
    ConcreteCompletionHandler c = new ConcreteCompletionHandler("1");
    assertEquals("1 done", c.handleEvent(handle));
  }
}
