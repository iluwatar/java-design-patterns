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

package com.iluwatar.commander.paymentservice;

import com.iluwatar.commander.Database;
import com.iluwatar.commander.exceptions.DatabaseUnavailableException;
import com.iluwatar.commander.paymentservice.PaymentService.PaymentRequest;
import java.util.Hashtable;

/**
 * PaymentDatabase is where the PaymentRequest is added, along with details.
 */

public class PaymentDatabase extends Database<PaymentRequest> {

  private Hashtable<String, PaymentRequest> data;

  public PaymentDatabase() {
    this.data = new Hashtable<>();
    //0-fail, 1-error, 2-success
  }

  @Override
  public PaymentRequest add(PaymentRequest r) {
    return data.put(r.transactionId, r);
  }

  @Override
  public PaymentRequest get(String requestId) {
    return data.get(requestId);
  }

}
