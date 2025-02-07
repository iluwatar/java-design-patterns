import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iluwatar.table.inheritance.App;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;

/**
 * Tests if the main method runs without throwing exceptions and prints expected output.
 */

class AppTest {

  @Test
  void testAppMainMethod() {

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    PrintStream printStream = new PrintStream(outContent);

    System.setOut(printStream);

    Logger logger = Logger.getLogger(App.class.getName());

    Handler handler = new ConsoleHandler() {
      @Override
      public void publish(java.util.logging.LogRecord recordObj) {
        printStream.println(getFormatter().format(recordObj));
      }
    };
    handler.setLevel(java.util.logging.Level.ALL);
    logger.addHandler(handler);

    App.main(new String[]{});

    String output = outContent.toString();

    assertTrue(output.contains("Retrieved Vehicle:"));
    assertTrue(output.contains("Toyota"));  // Car make
    assertTrue(output.contains("Ford"));    // Truck make
    assertTrue(output.contains("Retrieved Car:"));
    assertTrue(output.contains("Retrieved Truck:"));
  }
}




