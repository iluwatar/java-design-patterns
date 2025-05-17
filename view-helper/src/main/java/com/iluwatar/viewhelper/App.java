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

package com.iluwatar.viewhelper;

import java.math.BigDecimal;
import java.time.LocalDate;

/** The main application class that sets up and runs the View Helper pattern demo. */
public class App {
  /**
   * The entry point of the application.
   *
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    // Raw Product data (no formatting, no UI tags)
    var product =
        new Product(
            "Design patterns book", new BigDecimal("18.90"), LocalDate.of(2025, 4, 19), true);

    // Create view, viewHelper and viewHelper
    var helper = new ProductViewHelper();
    var view = new ConsoleProductView();
    var controller = new ProductController(helper, view);

    // Handle “request”
    controller.handle(product);
  }
}
