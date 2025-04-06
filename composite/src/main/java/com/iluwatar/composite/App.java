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
package com.iluwatar.composite;

import lombok.extern.slf4j.Slf4j;

/**
 * The Composite pattern is a partitioning design pattern. The Composite pattern describes that a
 * group of objects is to be treated in the same way as a single instance of an object. The intent
 * of a composite is to "compose" objects into tree structures to represent part-whole hierarchies.
 * Implementing the Composite pattern lets clients treat individual objects and compositions
 * uniformly.
 *
 * <p>In this example we have sentences composed of words composed of letters. All of the objects
 * can be treated through the same interface ({@link LetterComposite}).
 */
@Slf4j
public class App {

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {

    var messenger = new Messenger();

    LOGGER.info("Message from the orcs: ");
    messenger.messageFromOrcs().print();

    LOGGER.info("Message from the elves: ");
    messenger.messageFromElves().print();
  }
}
