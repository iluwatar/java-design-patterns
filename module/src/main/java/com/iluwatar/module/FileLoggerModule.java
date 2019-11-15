/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.module;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The FileLoggerModule is responsible for showing logs on File System.
 *
 * <p>The below example demonstrates a File logger module, which can print simple and error
 * messages in two designated files
 */
public final class FileLoggerModule {

  private static final Logger LOGGER = LoggerFactory.getLogger(FileLoggerModule.class);

  private static FileLoggerModule singleton = null;

  private static final String OUTPUT_FILE = "output.txt";
  private static final String ERROR_FILE = "error.txt";

  public PrintStream output = null;
  public PrintStream error = null;

  private FileLoggerModule() {
  }

  /**
   * Static method to get single instance of class.
   *
   * @return singleton instance of FileLoggerModule
   */
  public static FileLoggerModule getSingleton() {

    if (FileLoggerModule.singleton == null) {
      FileLoggerModule.singleton = new FileLoggerModule();
    }

    return FileLoggerModule.singleton;
  }

  /**
   * Following method performs the initialization.
   *
   * @throws FileNotFoundException if program is not able to find log files (output.txt and
   *                               error.txt)
   */
  public FileLoggerModule prepare() throws FileNotFoundException {

    LOGGER.debug("FileLoggerModule::prepare();");

    this.output = new PrintStream(new FileOutputStream(OUTPUT_FILE));
    this.error = new PrintStream(new FileOutputStream(ERROR_FILE));

    return this;
  }

  /**
   * Following method performs the finalization.
   */
  public void unprepare() {

    if (this.output != null) {

      this.output.flush();
      this.output.close();
    }

    if (this.error != null) {

      this.error.flush();
      this.error.close();
    }

    LOGGER.debug("FileLoggerModule::unprepare();");
  }

  /**
   * Used to print a message.
   *
   * @param value will be printed in file
   */
  public void printString(final String value) {
    this.output.println(value);
  }

  /**
   * Used to print a error message.
   *
   * @param value will be printed on error file
   */
  public void printErrorString(final String value) {
    this.error.println(value);
  }
}
