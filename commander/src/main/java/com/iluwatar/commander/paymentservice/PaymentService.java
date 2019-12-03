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

import com.iluwatar.commander.Service;
import com.iluwatar.commander.exceptions.DatabaseUnavailableException;

/**
 * The PaymentService class receives request from the {@link com.iluwatar.commander.Commander} and
 * adds to the {@link PaymentDatabase}.
 */

public class PaymentService extends Service {

  class PaymentRequest {
    String transactionId;
    float payment;
    boolean paid;

    PaymentRequest(String transactionId, float payment) {
      this.transactionId = transactionId;
      this.payment = payment;
      this.paid = false;
    }
  }

  public PaymentService(PaymentDatabase db, Exception... exc) {
    super(db, exc);
  }

  /**
   * Public method which will receive request from {@link com.iluwatar.commander.Commander}.
   */

  public String receiveRequest(Object... parameters) throws DatabaseUnavailableException {
    //it could also be sending a userid, payment details here or something, not added here
    var id = generateId();
    var req = new PaymentRequest(id, (float) parameters[0]);
    return updateDb(req);
  }

  protected String updateDb(Object... parameters) throws DatabaseUnavailableException {
    var req = (PaymentRequest) parameters[0];
    if (database.get(req.transactionId) == null || !req.paid) {
      database.add(req);
      req.paid = true;
      return req.transactionId;
    }
    return null;
  }
}
