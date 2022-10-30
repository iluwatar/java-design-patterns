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
package com.iluwatar.activeobject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Useful examples of common useage of the program.
 */
public class App {

  /**
   * Use this method to become familiar with the program.
   *
   * @param args arguments passed into the main method.
   */
  public static void main(String[] args) {
    try {
      ActiveDatabase activeDatabase = new ActiveDatabase("world", "root", "apple-trunks", "localhost:3306", "city");
      ActiveRow ar = new ActiveRow(activeDatabase, "101");
      ar.contents = new ArrayList<String>(
          Arrays.asList("101", "Godoy Cruz", "ARG", "Mendoza", "206998"));
      ar.write();
      System.out.println(ar.read());

    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
