/**
 * The MIT License Copyright (c) 2014 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.iluwatar.module;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * The Module pattern can be considered a Creational pattern and a Structural pattern. It manages
 * the creation and organization of other elements, and groups them as the structural pattern does.
 * An object that applies this pattern can provide the equivalent of a namespace, providing the
 * initialization and finalization process of a static class or a class with static members with
 * cleaner, more concise syntax and semantics.
 * <p>
 * The below example demonstrates a JUnit test for testing two different modules: File Logger and
 * Console Logger
 */
public final class FileLoggerModuleTest {

  private static final Logger LOGGER = Logger.getLogger(FileLoggerModuleTest.class);

  private static final String OUTPUT_FILE = "output.txt";
  private static final String ERROR_FILE = "error.txt";

  private static final String MESSAGE = "MESSAGE";
  private static final String ERROR = "ERROR";


  /**
   * This test verify that 'MESSAGE' is perfectly printed in output file
   * 
   * @throws IOException if program is not able to find log files (output.txt and error.txt)
   */
  @Test
  public void testFileMessage() throws IOException {

    /* Get singletong instance of File Logger Module */
    final FileLoggerModule fileLoggerModule = FileLoggerModule.getSingleton();

    /* Prepare the essential sub modules, to perform the sequence of jobs */
    fileLoggerModule.prepare();

    /* Print 'Message' in file */
    fileLoggerModule.printString(MESSAGE);

    /* Test if 'Message' is printed in file */
    assertEquals(readFirstLine(OUTPUT_FILE), MESSAGE);

    /* Unprepare to cleanup the modules */
    fileLoggerModule.unprepare();
  }

  /**
   * This test verify that nothing is printed in output file
   * 
   * @throws IOException if program is not able to find log files (output.txt and error.txt)
   */
  @Test
  public void testNoFileMessage() throws IOException {

    /* Get singletong instance of File Logger Module */
    final FileLoggerModule fileLoggerModule = FileLoggerModule.getSingleton();

    /* Prepare the essential sub modules, to perform the sequence of jobs */
    fileLoggerModule.prepare();

    /* Test if nothing is printed in file */
    assertEquals(readFirstLine(OUTPUT_FILE), null);

    /* Unprepare to cleanup the modules */
    fileLoggerModule.unprepare();
  }

  /**
   * This test verify that 'ERROR' is perfectly printed in error file
   * 
   * @throws FileNotFoundException if program is not able to find log files (output.txt and
   *         error.txt)
   */
  @Test
  public void testFileErrorMessage() throws FileNotFoundException {

    /* Get singletong instance of File Logger Module */
    final FileLoggerModule fileLoggerModule = FileLoggerModule.getSingleton();

    /* Prepare the essential sub modules, to perform the sequence of jobs */
    fileLoggerModule.prepare();

    /* Print 'Error' in file */
    fileLoggerModule.printErrorString(ERROR);

    /* Test if 'Message' is printed in file */
    assertEquals(readFirstLine(ERROR_FILE), ERROR);

    /* Unprepare to cleanup the modules */
    fileLoggerModule.unprepare();
  }

  /**
   * This test verify that nothing is printed in error file
   * 
   * @throws FileNotFoundException if program is not able to find log files (output.txt and
   *         error.txt)
   */
  @Test
  public void testNoFileErrorMessage() throws FileNotFoundException {

    /* Get singletong instance of File Logger Module */
    final FileLoggerModule fileLoggerModule = FileLoggerModule.getSingleton();

    /* Prepare the essential sub modules, to perform the sequence of jobs */
    fileLoggerModule.prepare();

    /* Test if nothing is printed in file */
    assertEquals(readFirstLine(ERROR_FILE), null);

    /* Unprepare to cleanup the modules */
    fileLoggerModule.unprepare();
  }

  /**
   * Utility method to read first line of a file
   * 
   * @param file as file name to be read
   * @return a string value as first line in file
   */
  private static final String readFirstLine(final String file) {

    String firstLine = null;
    BufferedReader bufferedReader = null;
    try {

      /* Create a buffered reader */
      bufferedReader = new BufferedReader(new FileReader(file));

      while (bufferedReader.ready()) {

        /* Read the line */
        firstLine = bufferedReader.readLine();
      }

      LOGGER.info("ModuleTest::readFirstLine() : firstLine : " + firstLine);

    } catch (final IOException e) {
      LOGGER.error("ModuleTest::readFirstLine()", e);
    } finally {

      if (bufferedReader != null) {
        try {
          bufferedReader.close();
        } catch (final IOException e) {
          LOGGER.error("ModuleTest::readFirstLine()", e);
        }
      }
    }

    return firstLine;
  }
}
