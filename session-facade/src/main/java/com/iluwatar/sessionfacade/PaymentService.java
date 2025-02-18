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

package com.iluwatar.sessionfacade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The PaymentService class is responsible for handling the selection and processing
 * of different payment methods. It provides functionality to select a payment method
 * (cash or credit card) and process the corresponding payment option. The class uses
 * logging to inform the client of the selected payment method.
 * It includes methods to:
 * - Select the payment method based on the client's choice.
 * - Process cash payments through the `cashPayment()` method.
 * - Process credit card payments through the `creditCardPayment()` method.
 */
public class PaymentService {
  /**
   * The constant LOGGER.
   */
  public static Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

  /**
   * Select payment method.
   *
   * @param method the method
   */
  public void selectPaymentMethod(String method) {
    if (method.equals("cash")) {
      cashPayment();
    } else if (method.equals("credit")) {
      creditCardPayment();
    } else {
      LOGGER.info("Unspecified payment method type");
    }
  }

  /**
   * Cash payment.
   */
  public void cashPayment() {
    LOGGER.info("Client have chosen cash payment option");
  }

  /**
   * Credit card payment.
   */
  public void creditCardPayment() {
    LOGGER.info("Client have chosen credit card payment option");
  }
}
