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

package com.iluwatar.commander;

import java.util.Hashtable;
import java.util.Random;

/**
 * Order class holds details of the order.
 */

public class Order { //can store all transactions ids also

  enum PaymentStatus {
    NotDone, Trying, Done
  }

  enum MessageSent {
    NoneSent, PaymentFail, PaymentTrying, PaymentSuccessful
  }

  final User user;
  final String item;
  public final String id;
  final float price;
  final long createdTime;
  private static final Random RANDOM = new Random();
  private static final String ALL_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
  private static final Hashtable<String, Boolean> USED_IDS = new Hashtable<String, Boolean>();
  PaymentStatus paid;
  MessageSent messageSent; //to avoid sending error msg on page and text more than once
  boolean addedToEmployeeHandle; //to avoid creating more to enqueue

  Order(User user, String item, float price) {
    this.createdTime = System.currentTimeMillis();
    this.user = user;
    this.item = item;
    this.price = price;
    String id = createUniqueId();
    if (USED_IDS.get(id) != null) {
      while (USED_IDS.get(id)) {
        id = createUniqueId();
      }
    }
    this.id = id;
    USED_IDS.put(this.id, true);
    this.paid = PaymentStatus.Trying;
    this.messageSent = MessageSent.NoneSent;
    this.addedToEmployeeHandle = false;
  }

  private String createUniqueId() {
    StringBuilder random = new StringBuilder();
    while (random.length() < 12) { // length of the random string.
      int index = (int) (RANDOM.nextFloat() * ALL_CHARS.length());
      random.append(ALL_CHARS.charAt(index));
    }
    return random.toString();
  }

}
