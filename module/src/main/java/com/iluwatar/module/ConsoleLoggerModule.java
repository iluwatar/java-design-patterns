/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

import java.io.PrintStream;
import lombok.extern.slf4j.Slf4j;

/**
 * The ConsoleLoggerModule is responsible for showing logs on System Console.
 *
 * <p>The below example demonstrates a Console logger module, which can print simple and error
 * messages in two designated formats
 */
@Slf4j
public final class ConsoleLoggerModule {

  private static ConsoleLoggerModule singleton = null;

  public PrintStream output = null;
  public PrintStream error = null;

  private ConsoleLoggerModule() {
  }

  /**
   * Static method to get single instance of class.
   *
   * @return singleton instance of ConsoleLoggerModule
   */
  public static ConsoleLoggerModule getSingleton() {

    if (ConsoleLoggerModule.singleton == null) {
      ConsoleLoggerModule.singleton = new ConsoleLoggerModule();
    }

    return ConsoleLoggerModule.singleton;
  }

  /**
   * Following method performs the initialization.
   */
  public ConsoleLoggerModule prepare() {

    LOGGER.debug("ConsoleLoggerModule::prepare();");

    this.output = new PrintStream(System.out);
    this.error = new PrintStream(System.err);

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

    LOGGER.debug("ConsoleLoggerModule::unprepare();");
  }

  /**
   * Used to print a message.
   *
   * @param value will be printed on console
   */
  public void printString(final String value) {
    this.output.println(value);
  }

  /**
   * Used to print a error message.
   *
   * @param value will be printed on error console
   */
  public void printErrorString(final String value) {
    this.error.println(value);
  }
}
