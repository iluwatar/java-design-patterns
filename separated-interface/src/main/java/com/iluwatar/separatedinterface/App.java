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

package com.iluwatar.separatedinterface;

import com.iluwatar.separatedinterface.invoice.InvoiceGenerator;
import com.iluwatar.separatedinterface.taxes.DomesticTaxCalculator;
import com.iluwatar.separatedinterface.taxes.ForeignTaxCalculator;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>The Separated Interface pattern encourages to separate the interface definition and
 * implementation in different packages. This allows the client to be completely unaware of the
 * implementation.</p>
 *
 * <p>In this class the {@link InvoiceGenerator} class is injected with different instances of
 * {@link com.iluwatar.separatedinterface.invoice.TaxCalculator} implementations located in separate
 * packages, to receive different responses for both of the implementations.</p>
 */
@Slf4j
public class App {

  public static final double PRODUCT_COST = 50.0;

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    //Create the invoice generator with product cost as 50 and foreign product tax
    var internationalProductInvoice = new InvoiceGenerator(PRODUCT_COST,
        new ForeignTaxCalculator());
    LOGGER.info("Foreign Tax applied: {}", "" + internationalProductInvoice.getAmountWithTax());

    //Create the invoice generator with product cost as 50 and domestic product tax
    var domesticProductInvoice = new InvoiceGenerator(PRODUCT_COST, new DomesticTaxCalculator());
    LOGGER.info("Domestic Tax applied: {}", "" + domesticProductInvoice.getAmountWithTax());
  }
}
