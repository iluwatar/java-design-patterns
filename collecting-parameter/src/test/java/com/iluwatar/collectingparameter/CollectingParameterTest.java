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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import java.util.LinkedList;
import java.util.Queue;

class CollectingParameterTest {

  @Test
  @Timeout(1000)
  void testCollectingParameter() {
    PrinterQueue printerQueue = PrinterQueue.getInstance();
    printerQueue.emptyQueue();

    PrinterItem item1 = new PrinterItem(PaperSizes.A4, 1, false, true);
    PrinterItem item2 = new PrinterItem(PaperSizes.A4, 10, true, false);
    PrinterItem item3 = new PrinterItem(PaperSizes.A4, 4, true, true);
    PrinterItem item4 = new PrinterItem(PaperSizes.A3, 9, false, false);
    PrinterItem item5 = new PrinterItem(PaperSizes.A3, 3, true, true);
    PrinterItem item6 = new PrinterItem(PaperSizes.A3, 3, false, true);
    PrinterItem item7 = new PrinterItem(PaperSizes.A3, 3, true, false);
    PrinterItem item8 = new PrinterItem(PaperSizes.A2, 1, false, false);
    PrinterItem item9 = new PrinterItem(PaperSizes.A2, 2, false, false);
    PrinterItem item10 = new PrinterItem(PaperSizes.A2, 1, true, false);
    PrinterItem item11 = new PrinterItem(PaperSizes.A2, 1, false, true);

    printerQueue.addPrinterItem(item1);
    printerQueue.addPrinterItem(item2);
    printerQueue.addPrinterItem(item3);
    printerQueue.addPrinterItem(item4);
    printerQueue.addPrinterItem(item5);
    printerQueue.addPrinterItem(item6);
    printerQueue.addPrinterItem(item7);
    printerQueue.addPrinterItem(item8);
    printerQueue.addPrinterItem(item9);
    printerQueue.addPrinterItem(item10);
    printerQueue.addPrinterItem(item11);

    Queue<PrinterItem> result = new LinkedList<>();
    App.addValidA4Papers(result);
    App.addValidA3Papers(result);
    App.addValidA2Papers(result);

    Queue<PrinterItem> testResult = new LinkedList<>();
    testResult.add(item1);
    testResult.add(item2);
    testResult.add(item4);
    testResult.add(item8);

    Assertions.assertArrayEquals(testResult.toArray(), result.toArray());
  }
}
