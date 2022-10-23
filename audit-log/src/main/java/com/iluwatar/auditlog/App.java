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
package com.iluwatar.auditlog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

/**
 * The AuditLog pattern is a simple log of changes, intended to be easily written and non-intrusive.
 *
 * <p>An audit log is the simplest method of tracking temporal information. The key idea is that
 * whenever something significant happens, a record is written to indicate what has happened and
 * when it has occurred. </p>
 *
 * <p>In this particular example, an audit log {@link AuditLog} is updated whenever
 * properties of the {@link Customer} class change, which is then stored within the file
 * <a href="file:./etc/log.txt">/etc/log.txt</a>. </p>
 */
public class App {
  /**
   * Main method.
   *
   * @param args Arguments for main method (unused).
   */
  public static void main(String[] args) {
    // empty the log file (don't do this) so that the pattern's size doesn't increase over time.
    try{
      Files.write(AuditLog.getLogFile().toPath(), "".getBytes(StandardCharsets.UTF_8),
              StandardOpenOption.TRUNCATE_EXISTING);
    } catch (IOException e) {
      System.out.println("Failed to clear log file.");
    }

    SimpleDate.setToday(new SimpleDate(2000, 5, 7));
    Customer john = new Customer("John Smith", 1);

    john.setAddress("1234 Street St", new SimpleDate(1999, 1, 4));

    // change dates to simulate time passing
    SimpleDate.setToday(new SimpleDate(2001, 6, 3));

    // note that john's address and name have changed in the past
    john.setName("John Johnson", new SimpleDate(2001, 4, 17));
    john.setAddress("4321 House Ave", new SimpleDate(2000, 8, 23));

    // Print out the log file.
    try {
      File logFile = AuditLog.getLogFile();
      BufferedReader br = new BufferedReader(new FileReader(logFile));
      String line;
      while ((line = br.readLine()) != null) {
        System.out.println(line);
      }
    } catch (IOException e) {
      System.out.println("Failed to get log file for reading.");
    }

  }
}
