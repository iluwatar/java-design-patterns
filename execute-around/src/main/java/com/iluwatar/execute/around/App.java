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
package com.iluwatar.execute.around;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;

/**
 * The Execute Around idiom specifies executable code before and after a method. Typically,
 * the idiom is used when the API has methods to be executed in pairs, such as resource
 * allocation/deallocation or lock acquisition/release.
 *
 * <p>In this example, we have {@link SimpleFileWriter} class that opens and closes the file for
 * the user. The user specifies only what to do with the file by providing the {@link
 * FileWriterAction} implementation.
 */
@Slf4j
public class App {

  /**
   * Program entry point.
   */
  public static void main(String[] args) throws IOException {

    // create the file writer and execute the custom action
    FileWriterAction writeHello = writer -> writer.write("Gandalf was here");
    new SimpleFileWriter("testfile.txt", writeHello);

    // print the file contents
    try (var scanner = new Scanner(new File("testfile.txt"))) {
      while (scanner.hasNextLine()) {
        LOGGER.info(scanner.nextLine());
      }
    }
  }
}
