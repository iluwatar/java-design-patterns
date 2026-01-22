package com.iluwatar.threadspecificstorage;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for APP class
 */
class AppTest {
  @Test
  void testMainMethod() {
    // Capture system output
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    // Run the main method
    App.main(new String[]{});

    // Give some time for threads to execute
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }

    // Verify output contains expected log messages
    String output = outContent.toString();
    assertTrue(output.contains("Start handling request with token"),
        "Should contain request handling start messages");

    // Restore system output
    System.setOut(System.out);
  }
}