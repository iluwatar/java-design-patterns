package com.iluwatar.templateview;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TemplateViewRobot {

  private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  public TemplateViewRobot render(TemplateView view) {
    try {
      System.setOut(new PrintStream(outputStream));
      view.render();
    } finally {
      System.setOut(originalOut);
    }
    return this;
  }

  public TemplateViewRobot verifyContent(String expectedContent) {
    String renderedOutput = outputStream.toString();
    assertTrue(renderedOutput.contains(expectedContent), 
        String.format("Expected content '%s' not found in output:\n%s", expectedContent, renderedOutput));
    return this;
  }

  public TemplateViewRobot reset() {
    outputStream.reset();
    return this;
  }
}
