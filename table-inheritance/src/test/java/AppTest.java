/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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




