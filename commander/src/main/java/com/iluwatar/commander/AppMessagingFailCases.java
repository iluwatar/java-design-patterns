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

import com.iluwatar.commander.employeehandle.EmployeeDatabase;
import com.iluwatar.commander.employeehandle.EmployeeHandle;
import com.iluwatar.commander.exceptions.DatabaseUnavailableException;
import com.iluwatar.commander.messagingservice.MessagingDatabase;
import com.iluwatar.commander.messagingservice.MessagingService;
import com.iluwatar.commander.paymentservice.PaymentDatabase;
import com.iluwatar.commander.paymentservice.PaymentService;
import com.iluwatar.commander.shippingservice.ShippingDatabase;
import com.iluwatar.commander.shippingservice.ShippingService;
import com.iluwatar.commander.queue.QueueDatabase;

/**
 * AppMessagingFailCases class looks at possible cases when Messaging service is 
 * available/unavailable.
 */

public class AppMessagingFailCases {
  final int numOfRetries = 3;
  final long retryDuration = 30000;
  final long queueTime = 240000; //4 mins
  final long queueTaskTime = 60000; //1 min
  final long paymentTime = 120000; //2 mins
  final long messageTime = 150000; //2.5 mins
  final long employeeTime = 240000; //4 mins

  void messagingDatabaseUnavailableCasePaymentSuccess() throws Exception {
    //rest is successful
    PaymentService ps = new PaymentService(new PaymentDatabase());  
    ShippingService ss = new ShippingService(new ShippingDatabase());
    MessagingService ms = new MessagingService(new MessagingDatabase(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException());
    EmployeeHandle eh = new EmployeeHandle(new EmployeeDatabase());
    QueueDatabase qdb = new QueueDatabase();
    Commander c = new Commander(eh,ps,ss,ms,qdb,numOfRetries,retryDuration,
        queueTime,queueTaskTime,paymentTime,messageTime,employeeTime);
    User user = new User("Jim", "ABCD");
    Order order = new Order(user, "book", 10f);
    c.placeOrder(order);
  }

  void messagingDatabaseUnavailableCasePaymentError() throws Exception {
    //rest is successful
    PaymentService ps = new PaymentService(new PaymentDatabase(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException());  
    ShippingService ss = new ShippingService(new ShippingDatabase());
    MessagingService ms = new MessagingService(new MessagingDatabase(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(), new DatabaseUnavailableException());
    EmployeeHandle eh = new EmployeeHandle(new EmployeeDatabase());
    QueueDatabase qdb = new QueueDatabase();
    Commander c = new Commander(eh,ps,ss,ms,qdb,numOfRetries,retryDuration,
        queueTime,queueTaskTime,paymentTime,messageTime,employeeTime);
    User user = new User("Jim", "ABCD");
    Order order = new Order(user, "book", 10f);
    c.placeOrder(order);
  }

  void messagingDatabaseUnavailableCasePaymentFailure() throws Exception { 
    //rest is successful
    PaymentService ps = new PaymentService(new PaymentDatabase(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException());  
    ShippingService ss = new ShippingService(new ShippingDatabase());
    MessagingService ms = new MessagingService(new MessagingDatabase(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException());
    EmployeeHandle eh = new EmployeeHandle(new EmployeeDatabase());
    QueueDatabase qdb = new QueueDatabase(new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException());
    Commander c = new Commander(eh,ps,ss,ms,qdb,numOfRetries,retryDuration,queueTime,queueTaskTime,
        paymentTime,messageTime,employeeTime);
    User user = new User("Jim", "ABCD");
    Order order = new Order(user, "book", 10f);
    c.placeOrder(order);
  }

  void messagingSuccessCase() throws Exception {
    //done here
    PaymentService ps = new PaymentService(new PaymentDatabase(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException(), new DatabaseUnavailableException());  
    ShippingService ss = new ShippingService(new ShippingDatabase());
    MessagingService ms = new MessagingService(new MessagingDatabase(), new DatabaseUnavailableException(),
        new DatabaseUnavailableException());
    EmployeeHandle eh = new EmployeeHandle(new EmployeeDatabase());
    QueueDatabase qdb = new QueueDatabase();
    Commander c = new Commander(eh,ps,ss,ms,qdb,numOfRetries,retryDuration,
        queueTime,queueTaskTime,paymentTime,messageTime,employeeTime);
    User user = new User("Jim", "ABCD");
    Order order = new Order(user, "book", 10f);
    c.placeOrder(order);
  }

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  
  public static void main(String[] args) throws Exception {
    AppMessagingFailCases amfc = new AppMessagingFailCases();
    //amfc.messagingDatabaseUnavailableCasePaymentSuccess();
    //amfc.messagingDatabaseUnavailableCasePaymentError(); 
    //amfc.messagingDatabaseUnavailableCasePaymentFailure(); 
    amfc.messagingSuccessCase();
  }
}
