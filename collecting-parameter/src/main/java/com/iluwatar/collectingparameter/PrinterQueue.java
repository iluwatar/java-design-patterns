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
package com.iluwatar.collectingparameter;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * This class represents a singleton Printer Queue. It contains a queue that can be filled up with {@link PrinterItem}.
 **/
public class PrinterQueue {

  static PrinterQueue currentInstance = null;
  private final Queue<PrinterItem> printerItemQueue;

  /**
   * This class is a singleton. The getInstance method will ensure that only one instance exists at a time.
   */
  public static PrinterQueue getInstance() {
    if (Objects.isNull(currentInstance)) {
      currentInstance = new PrinterQueue();
    }
    return currentInstance;
  }

  /**
   * Empty the printer queue.
   */
  public void emptyQueue() {
    currentInstance.getPrinterQueue().clear();
  }

  /**
   * Private constructor prevents instantiation, unless using the getInstance() method.
   */
  private PrinterQueue() {
    printerItemQueue = new LinkedList<>();
  }

  public Queue<PrinterItem> getPrinterQueue() {
    return currentInstance.printerItemQueue;
  }

  /**
   * Adds a single print job to the queue.
   *
   * @param printerItem The printing job to be added to the queue
   */
  public void addPrinterItem(PrinterItem printerItem) {
    currentInstance.getPrinterQueue().add(printerItem);
  }

}
