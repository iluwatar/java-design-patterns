package com.iluwatar.threadspecificstorage;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

/** Tests for APP class */
class AppTest {
  @Test
  void testMainMethod() {
    // Capture system output
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));

    // Run the main method
    App.main(new String[] {});

    // Give some time for threads to execute
    await()
        .atMost(5, SECONDS)
        .pollInterval(100, MILLISECONDS)
        .until(() -> outContent.toString().contains("Start handling request with token"));

    // Verify output contains expected log messages
    String output = outContent.toString();
    assertTrue(
        output.contains("Start handling request with token"),
        "Should contain request handling start messages");

    // Restore system output
    System.setOut(System.out);
  }
}
