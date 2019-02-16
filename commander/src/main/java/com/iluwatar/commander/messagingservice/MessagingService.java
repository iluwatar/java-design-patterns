/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Sepp�l�
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

package com.iluwatar.commander.messagingservice;

import com.iluwatar.commander.Service;
import com.iluwatar.commander.exceptions.DatabaseUnavailableException;

/**
 * The MessagingService is used to send messages to user regarding their order and 
 * payment status. In case an error is encountered in payment and this service is
 * found to be unavailable, the order is added to the {@link EmployeeDatabase}.
 */

public class MessagingService extends Service {

  enum MessageToSend {
    PaymentFail, PaymentTrying, PaymentSuccessful
  };

  class MessageRequest {
    String reqId;
    MessageToSend msg;

    MessageRequest(String reqId, MessageToSend msg) {
      this.reqId = reqId;
      this.msg = msg;
    }
  }

  public MessagingService(MessagingDatabase db, Exception...exc) {
    super(db, exc);
  }

  /**
   * Public method which will receive request from {@link Commander}.
   */
  
  public String receiveRequest(Object...parameters) throws DatabaseUnavailableException {
    int messageToSend = (int) parameters[0];
    String rId = generateId();
    MessageToSend msg = null;
    if (messageToSend == 0) {
      msg = MessageToSend.PaymentFail;
    } else if (messageToSend == 1) {
      msg = MessageToSend.PaymentTrying;
    } else { //messageToSend == 2
      msg = MessageToSend.PaymentSuccessful;
    }
    MessageRequest req = new MessageRequest(rId, msg);
    return updateDb(req);
  }

  protected String updateDb(Object...parameters) throws DatabaseUnavailableException {
    MessageRequest req = (MessageRequest) parameters[0];
    if (this.database.get(req.reqId) == null) { //idempotence, in case db fails here
      database.add(req); //if successful:
      System.out.println(sendMessage(req.msg));
      return req.reqId;
    }
    return null;
  }

  String sendMessage(MessageToSend m) {
    if (m.equals(MessageToSend.PaymentSuccessful)) {
      return "Msg: Your order has been placed and paid for successfully! Thank you for shopping with us!";
    } else if (m.equals(MessageToSend.PaymentTrying)) {
      return "Msg: There was an error in your payment process, we are working on it and will return back to you"
          + " shortly. Meanwhile, your order has been placed and will be shipped.";
    } else {
      return "Msg: There was an error in your payment process. Your order is placed and has been converted to COD."
          + " Please reach us on CUSTOMER-CARE-NUBER in case of any queries. Thank you for shopping with us!";
    }
  }
}
