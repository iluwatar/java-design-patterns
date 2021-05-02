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

package com.iluwatar.execute.around;

import java.io.IOException;

/**
 * The Execute Around idiom specifies some code to be executed before and after a method. Typically
 * the idiom is used when the API has methods to be executed in pairs, such as resource
 * allocation/deallocation or lock acquisition/release.
 *
 * <p>In this example, we have {@link SimpleFileWriter} class that opens and closes the file for
 * the user. The user specifies only what to do with the file by providing the {@link
 * FileWriterAction} implementation.
 */
public class App {

  /**
   * Program entry point.
   */
  public static void main(String[] args) throws IOException {

    FileWriterAction writeHello = writer -> {
      writer.write("Hello");
      writer.append(" ");
      writer.append("there!");
    };
    new SimpleFileWriter("testfile.txt", writeHello);
  }
}
