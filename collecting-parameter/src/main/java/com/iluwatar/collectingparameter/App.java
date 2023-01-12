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
import java.util.Queue;

/**
 * The Collecting Parameter Design Pattern aims to return a result that is the collaborative result of several
 * methods. This design pattern uses a 'collecting parameter' that is passed to several functions, accumulating results
 * as it travels from method-to-method. This is different to the Composed Method design pattern, where a single
 * collection is modified via several methods.
 *
 * <p>This example is inspired by Kent Beck's example in his book, 'Smalltalk Best Practice Patterns'. The context for this
 * situation is that there is a single printer queue {@link PrinterQueue} that holds numerous print jobs
 * {@link PrinterItem} that must be distributed to various print centers.
 * Each print center has its own requirements and printing limitations. In this example, the following requirements are:
 * If an A4 document is coloured, it must also be single-sided. All other non-coloured A4 documents are accepted.
 * All A3 documents must be non-coloured and single sided. All A2 documents must be a single page, single sided, and
 * non-coloured.
 *
 * <p>A collecting parameter (the result variable) is used to filter the global printer queue so that it meets the
 * requirements for this centre,
 **/

public class App {
  static PrinterQueue printerQueue = PrinterQueue.getInstance();

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    /*
      Initialising the printer queue with jobs
    */
    printerQueue.addPrinterItem(new PrinterItem(PaperSizes.A4, 5, false, false));
    printerQueue.addPrinterItem(new PrinterItem(PaperSizes.A3, 2, false, false));
    printerQueue.addPrinterItem(new PrinterItem(PaperSizes.A2, 5, false, false));

    /*
      This variable is the collecting parameter, and will store the policy abiding print jobs.
    */
    var result = new LinkedList<PrinterItem>();

    /*
      Adding A4, A3, and A2 papers that obey the policy
    */
    addValidA4Papers(result);
    addValidA3Papers(result);
    addValidA2Papers(result);
  }

  /**
   * Adds A4 document jobs to the collecting parameter according to some policy that can be whatever the client
   * (the print center) wants.
   *
   * @param printerItemsCollection the collecting parameter
   */
  public static void addValidA4Papers(Queue<PrinterItem> printerItemsCollection) {
    /*
      Iterate through the printer queue, and add A4 papers according to the correct policy to the collecting parameter,
      which is 'printerItemsCollection' in this case.
     */
    for (PrinterItem nextItem : printerQueue.getPrinterQueue()) {
      if (nextItem.paperSize.equals(PaperSizes.A4)) {
        var isColouredAndSingleSided = nextItem.isColour && !nextItem.isDoubleSided;
        if (isColouredAndSingleSided || !nextItem.isColour) {
          printerItemsCollection.add(nextItem);
        }
      }
    }
  }

  /**
   * Adds A3 document jobs to the collecting parameter according to some policy that can be whatever the client
   * (the print center) wants. The code is similar to the 'addA4Papers' method. The code can be changed to accommodate
   * the wants of the client.
   *
   * @param printerItemsCollection the collecting parameter
   */
  public static void addValidA3Papers(Queue<PrinterItem> printerItemsCollection) {
    for (PrinterItem nextItem : printerQueue.getPrinterQueue()) {
      if (nextItem.paperSize.equals(PaperSizes.A3)) {

        // Encoding the policy into a Boolean: the A3 paper cannot be coloured and double-sided at the same time
        var isNotColouredAndSingleSided = !nextItem.isColour && !nextItem.isDoubleSided;
        if (isNotColouredAndSingleSided) {
          printerItemsCollection.add(nextItem);
        }
      }
    }
  }

  /**
   * Adds A2 document jobs to the collecting parameter according to some policy that can be whatever the client
   * (the print center) wants. The code is similar to the 'addA4Papers' method. The code can be changed to accommodate
   * the wants of the client.
   *
   * @param printerItemsCollection the collecting parameter
   */
  public static void addValidA2Papers(Queue<PrinterItem> printerItemsCollection) {
    for (PrinterItem nextItem : printerQueue.getPrinterQueue()) {
      if (nextItem.paperSize.equals(PaperSizes.A2)) {

        // Encoding the policy into a Boolean: the A2 paper must be single page, single-sided, and non-coloured.
        var isNotColouredSingleSidedAndOnePage = nextItem.pageCount == 1 && !nextItem.isDoubleSided
                && !nextItem.isColour;
        if (isNotColouredSingleSidedAndOnePage) {
          printerItemsCollection.add(nextItem);
        }
      }
    }
  }
}
