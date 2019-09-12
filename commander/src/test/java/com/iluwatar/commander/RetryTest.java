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

package com.iluwatar.commander;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

import com.iluwatar.commander.Order;
import com.iluwatar.commander.Retry;
import com.iluwatar.commander.User;
import com.iluwatar.commander.Retry.HandleErrorIssue;
import com.iluwatar.commander.Retry.Operation;
import com.iluwatar.commander.exceptions.DatabaseUnavailableException;
import com.iluwatar.commander.exceptions.ItemUnavailableException;

class RetryTest {

  @Test
  void performTest() {
    Retry.Operation op = (l) -> { 
      if (!l.isEmpty()) {
        throw l.remove(0);
      }
      return; 
    };
    Retry.HandleErrorIssue<Order> handleError = (o,e) -> { 
      return; 
    };
    Retry<Order> r1 = new Retry<Order>(op, handleError, 3, 30000,
        e -> DatabaseUnavailableException.class.isAssignableFrom(e.getClass()));
    Retry<Order> r2 = new Retry<Order>(op, handleError, 3, 30000,
        e -> DatabaseUnavailableException.class.isAssignableFrom(e.getClass()));
    User user = new User("Jim", "ABCD");
    Order order = new Order(user, "book", 10f);
    ArrayList<Exception> arr1 = new ArrayList<Exception>(Arrays.asList(new Exception[]
        {new ItemUnavailableException(), new DatabaseUnavailableException()}));
    try {
      r1.perform(arr1, order);
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    ArrayList<Exception> arr2 = new ArrayList<Exception>(Arrays.asList(new Exception[]
        {new DatabaseUnavailableException(), new ItemUnavailableException()}));
    try {
      r2.perform(arr2, order);
    } catch (Exception e1) {
      e1.printStackTrace();
    }
    //r1 stops at ItemUnavailableException, r2 retries because it encounters DatabaseUnavailableException
    assertTrue(arr1.size() == 1 && arr2.size() == 0);
  }

}
