/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.separatedinterface;

import com.iluwatar.separatedinterface.invoice.InvoiceGenerator;
import com.iluwatar.separatedinterface.taxes.DomesticTax;
import com.iluwatar.separatedinterface.taxes.ForeignTax;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>The Strategy pattern (also known as the policy pattern) is a software design pattern that
 * enables an algorithm's behavior to be selected at runtime.</p>
 *
 * <p>Before Java 8 the Strategies needed to be separate classes forcing the developer
 * to write lots of boilerplate code. With modern Java it is easy to pass behavior with method
 * references and lambdas making the code shorter and more readable.</p>
 *
 */
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  public static final double PRODUCT_COST = 50.0;

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    //Create the invoice generator with product cost as 50 and foreign product tax
    var internationalProductInvoice = new InvoiceGenerator(PRODUCT_COST, new ForeignTax());
    LOGGER.info("Foreign Tax applied: {}", "" + internationalProductInvoice.getAmountWithTax());

    //Create the invoice generator with product cost as 50 and domestic product tax
    var domesticProductInvoice = new InvoiceGenerator(PRODUCT_COST, new DomesticTax());
    LOGGER.info("Domestic Tax applied: {}", "" + domesticProductInvoice.getAmountWithTax());
  }
}
