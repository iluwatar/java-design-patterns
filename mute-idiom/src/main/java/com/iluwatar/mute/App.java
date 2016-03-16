/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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

package com.iluwatar.mute;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Mute pattern is utilized when we need to suppress an exception due to an API flaw or in 
 * situation when all we can do to handle the exception is to log it. 
 * This pattern should not be used everywhere. It is very important to logically handle the 
 * exceptions in a system, but some situations like the ones described above require this pattern, 
 * so that we don't need to repeat 
 * <pre>
 * <code>
 *   try {
 *     // code that may throwing exception we need to ignore or may never be thrown
 *   } catch (Exception ex) {
 *     // ignore by logging or throw error if unexpected exception occurs
 *   }
 * </code>
 * </pre> every time we need to ignore an exception.
 * 
 */
public class App {

  /**
   * Program entry point.
   * 
   * @param args command line args.
   * @throws Exception if any exception occurs
   */
  public static void main(String[] args) throws Exception {

    useOfLoggedMute();

    useOfMute();
  }

  /*
   * Typically used when the API declares some exception but cannot do so. Usually a 
   * signature mistake.In this example out is not supposed to throw exception as it is a
   * ByteArrayOutputStream. So we utilize mute, which will throw AssertionError if unexpected
   * exception occurs.
   */
  private static void useOfMute() {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Mute.mute(() -> out.write("Hello".getBytes()));
  }

  private static void useOfLoggedMute() throws SQLException {
    Connection connection = null;
    try {
      connection = openConnection();
      readStuff(connection);
    } finally {
      closeConnection(connection);
    }
  }

  /*
   * All we can do while failed close of connection is to log it.
   */
  private static void closeConnection(Connection connection) {
    Mute.loggedMute(() -> connection.close());
  }

  private static void readStuff(Connection connection) throws SQLException {
    try (Statement statement = connection.createStatement()) {
      System.out.println("Read data from statement");
    }
  }

  private static Connection openConnection() throws SQLException {
    Connection mockedConnection = mock(Connection.class);
    doThrow(SQLException.class).when(mockedConnection).close();
    return mockedConnection;
  }
}
