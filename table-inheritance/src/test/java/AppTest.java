import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iluwatar.table.inheritance.App;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;

class AppTest {

  /**
   * Tests if the main method runs without throwing exceptions and prints expected output.
   */
  @Test
   void testAppMainMethod() {

    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    System.setOut(new PrintStream(outContent));


    App.main(new String[]{});

    String output = outContent.toString();

    assertTrue(output.contains("Retrieved Vehicle:"));
    assertTrue(output.contains("Retrieved Car:"));
    assertTrue(output.contains("Retrieved Truck:"));
  }
}
