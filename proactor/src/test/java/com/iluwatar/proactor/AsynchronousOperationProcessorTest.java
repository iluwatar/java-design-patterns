package com.iluwatar.proactor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class AsynchronousOperationProcessorTest {

  AsynchronousOperationProcessor a = new AsynchronousOperationProcessor();

  @Test
  void shouldExecuteWithoutException() {
    ConcreteCompletionHandler handler = new ConcreteCompletionHandler("long");
    assertDoesNotThrow(() -> a.register("task2",handler));
  }

  @Test
  void testRegister1() throws Exception {
    ConcreteCompletionHandler handler = new ConcreteCompletionHandler("short");
    assertEquals("short done", a.register("task1",handler));
  }

  @Test
  void testRegister2() throws Exception {
    ConcreteCompletionHandler handler = new ConcreteCompletionHandler("long");
    assertEquals("long done", a.register("task2",handler));
  }
}
