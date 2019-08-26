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

import java.util.ArrayList;
import org.apache.log4j.Logger;
import com.iluwatar.commander.employeehandle.EmployeeHandle;
import com.iluwatar.commander.exceptions.DatabaseUnavailableException;
import com.iluwatar.commander.exceptions.ItemUnavailableException;
import com.iluwatar.commander.exceptions.PaymentDetailsErrorException;
import com.iluwatar.commander.exceptions.ShippingNotPossibleException;
import com.iluwatar.commander.messagingservice.MessagingService;
import com.iluwatar.commander.Order.MessageSent;
import com.iluwatar.commander.Order.PaymentStatus;
import com.iluwatar.commander.paymentservice.PaymentService;
import com.iluwatar.commander.queue.QueueDatabase;
import com.iluwatar.commander.queue.QueueTask;
import com.iluwatar.commander.queue.QueueTask.TaskType;
import com.iluwatar.commander.shippingservice.ShippingService;

/**
 *<p>Commander pattern is used to handle all issues that can come up while making a
 * distributed transaction. The idea is to have a commander, which coordinates the
 * execution of all instructions and ensures proper completion using retries and
 * taking care of idempotence. By queueing instructions while they haven't been done,
 * we can ensure a state of 'eventual consistency'.</p>
 * <p>In our example, we have an e-commerce application. When the user places an order,
 * the shipping service is intimated first. If the service does not respond for some
 * reason, the order is not placed. If response is received, the commander then calls
 * for the payment service to be intimated. If this fails, the shipping still takes
 * place (order converted to COD) and the item is queued. If the queue is also found
 * to be unavailable, the payment is noted to be not done and this is added to an
 * employee database. Three types of messages are sent to the user - one, if payment
 * succeeds; two, if payment fails definitively; and three, if payment fails in the
 * first attempt. If the message is not sent, this is also queued and is added to employee
 * db. We also have a time limit for each instruction to be completed, after which, the
 * instruction is not executed, thereby ensuring that resources are not held for too long.
 * In the rare occasion in which everything fails, an individual would have to step in to
 * figure out how to solve the issue.</p>
 * <p>We have abstract classes {@link Database} and {@link Service} which are extended
 * by all the databases and services. Each service has a database to be updated, and 
 * receives request from an outside user (the {@link Commander} class here). There are
 * 5 microservices - {@link ShippingService}, {@link PaymentService}, {@link MessagingService},
 * {@link EmployeeHandle} and a {@link QueueDatabase}. We use retries to execute any
 * instruction using {@link Retry} class, and idempotence is ensured by going through some
 * checks before making requests to services and making change in {@link Order} class fields
 * if request succeeds or definitively fails. There are 5 classes - 
 * {@link AppShippingFailCases}, {@link AppPaymentFailCases}, {@link AppMessagingFailCases}, 
 * {@link AppQueueFailCases} and {@link AppEmployeeDbFailCases}, which look at the different
 * scenarios that may be encountered during the placing of an order.</p>
 */

public class Commander {

  private final QueueDatabase queue;
  private final EmployeeHandle employeeDb;
  private final PaymentService paymentService;
  private final ShippingService shippingService;
  private final MessagingService messagingService;
  private int queueItems = 0; //keeping track here only so don't need access to queue db to get this
  private final int numOfRetries;
  private final long retryDuration;
  private final long queueTime;
  private final long queueTaskTime;
  private final long paymentTime;
  private final long messageTime;
  private final long employeeTime; 
  private boolean finalSiteMsgShown;
  static final Logger LOG = Logger.getLogger(Commander.class);
  //we could also have another db where it stores all orders
  
  Commander(EmployeeHandle empDb, PaymentService pService, ShippingService sService,
      MessagingService mService, QueueDatabase qdb, int numOfRetries, long retryDuration,
      long queueTime, long queueTaskTime, long paymentTime, long messageTime, long employeeTime) {
    this.paymentService = pService;
    this.shippingService = sService;
    this.messagingService = mService;
    this.employeeDb = empDb;
    this.queue = qdb;
    this.numOfRetries = numOfRetries;
    this.retryDuration = retryDuration;
    this.queueTime = queueTime;
    this.queueTaskTime = queueTaskTime;
    this.paymentTime = paymentTime;
    this.messageTime = messageTime;
    this.employeeTime = employeeTime;
    this.finalSiteMsgShown = false;   
  }

  void placeOrder(Order order) throws Exception {
    sendShippingRequest(order);
  }

  private void sendShippingRequest(Order order) throws Exception {
    ArrayList<Exception> list = shippingService.exceptionsList;
    Retry.Operation op = (l) -> {
      if (!l.isEmpty()) {
        if (DatabaseUnavailableException.class.isAssignableFrom(l.get(0).getClass())) {
          LOG.debug("Order " + order.id + ": Error in connecting to shipping service, trying again..");
        } else {
          LOG.debug("Order " + order.id + ": Error in creating shipping request..");
        }
        throw l.remove(0);
      } 
      String transactionId = shippingService.receiveRequest(order.item, order.user.address); 
      //could save this transaction id in a db too
      LOG.info("Order " + order.id + ": Shipping placed successfully, transaction id: " + transactionId);
      System.out.println("Order has been placed and will be shipped to you. Please wait while we make your"
          + " payment... "); 
      sendPaymentRequest(order);       
      return; 
    };
    Retry.HandleErrorIssue<Order> handleError = (o,err) -> {
      if (ShippingNotPossibleException.class.isAssignableFrom(err.getClass())) {
        System.out.println("Shipping is currently not possible to your address. We are working on the problem "
            + "and will get back to you asap.");
        finalSiteMsgShown = true;
        LOG.info("Order " + order.id + ": Shipping not possible to address, trying to add problem to employee db..");
        employeeHandleIssue(o);
      } else if (ItemUnavailableException.class.isAssignableFrom(err.getClass())) {
        System.out.println("This item is currently unavailable. We will inform you as soon as the item becomes "
            + "available again.");
        finalSiteMsgShown = true;
        LOG.info("Order " + order.id + ": Item " + order.item + " unavailable, trying to add problem to employee "
            + "handle..");
        employeeHandleIssue(o);
      } else {
        System.out.println("Sorry, there was a problem in creating your order. Please try later.");
        LOG.error("Order " + order.id + ": Shipping service unavailable, order not placed..");
        finalSiteMsgShown = true;
      }
      return; 
    };
    Retry<Order> r = new Retry<Order>(op, handleError, numOfRetries, retryDuration,
        e -> DatabaseUnavailableException.class.isAssignableFrom(e.getClass()));
    r.perform(list, order);
  }

  private void sendPaymentRequest(Order order) {
    if (System.currentTimeMillis() - order.createdTime >= this.paymentTime) {
      if (order.paid.equals(PaymentStatus.Trying)) {
        order.paid = PaymentStatus.NotDone;
        sendPaymentFailureMessage(order);
        LOG.error("Order " + order.id + ": Payment time for order over, failed and returning..");
      } //if succeeded or failed, would have been dequeued, no attempt to make payment     
      return;
    }
    ArrayList<Exception> list = paymentService.exceptionsList;
    Thread t = new Thread(new Runnable() {
      @Override
      public void run() {
        Retry.Operation op = (l) -> {
          if (!l.isEmpty()) {
            if (DatabaseUnavailableException.class.isAssignableFrom(l.get(0).getClass())) {
              LOG.debug("Order " + order.id + ": Error in connecting to payment service, trying again..");
            } else {
              LOG.debug("Order " + order.id + ": Error in creating payment request..");
            }
            throw l.remove(0); 
          } 
          if (order.paid.equals(PaymentStatus.Trying)) {
            String transactionId = paymentService.receiveRequest(order.price);
            order.paid = PaymentStatus.Done; 
            LOG.info("Order " + order.id + ": Payment successful, transaction Id: " + transactionId);
            if (!finalSiteMsgShown) { 
              System.out.println("Payment made successfully, thank you for shopping with us!!"); 
              finalSiteMsgShown = true; 
            } 
            sendSuccessMessage(order); 
            return; 
          } 
        };
        Retry.HandleErrorIssue<Order> handleError = (o,err) -> {
          if (PaymentDetailsErrorException.class.isAssignableFrom(err.getClass())) {
            if (!finalSiteMsgShown) {
              System.out.println("There was an error in payment. Your account/card details may have been incorrect. "
                  + "Meanwhile, your order has been converted to COD and will be shipped.");
              finalSiteMsgShown = true;
            }
            LOG.error("Order " + order.id + ": Payment details incorrect, failed..");
            o.paid = PaymentStatus.NotDone;
            sendPaymentFailureMessage(o);
          } else {
            try {
              if (o.messageSent.equals(MessageSent.NoneSent)) {
                if (!finalSiteMsgShown) {
                  System.out.println("There was an error in payment. We are on it, and will get back to you "
                      + "asap. Don't worry, your order has been placed and will be shipped.");
                  finalSiteMsgShown = true;
                }
                LOG.warn("Order " + order.id + ": Payment error, going to queue..");
                sendPaymentPossibleErrorMsg(o);
              }
              if (o.paid.equals(PaymentStatus.Trying) && System.currentTimeMillis() - o.createdTime < paymentTime) {
                QueueTask qt = new QueueTask(o, TaskType.Payment,-1);
                updateQueue(qt);
              } 
            } catch (InterruptedException e1) {
              e1.printStackTrace();
            } 
          } 
          return; 
        };
        Retry<Order> r = new Retry<Order>(op, handleError, numOfRetries, retryDuration,
            e -> DatabaseUnavailableException.class.isAssignableFrom(e.getClass()));
        try {
          r.perform(list, order);
        } catch (Exception e1) {
          e1.printStackTrace();
        } 
      }
    });
    t.start();
  }

  private void updateQueue(QueueTask qt) throws InterruptedException {
    if (System.currentTimeMillis() - qt.order.createdTime >= this.queueTime) {
      //since payment time is lesser than queuetime it would have already failed..additional check not needed
      LOG.trace("Order " + qt.order.id + ": Queue time for order over, failed..");
      return;
    } else if ((qt.taskType.equals(TaskType.Payment) && !qt.order.paid.equals(PaymentStatus.Trying))
        || (qt.taskType.equals(TaskType.Messaging) && ((qt.messageType == 1 
        && !qt.order.messageSent.equals(MessageSent.NoneSent))
        || qt.order.messageSent.equals(MessageSent.PaymentFail) 
        || qt.order.messageSent.equals(MessageSent.PaymentSuccessful)))
        || (qt.taskType.equals(TaskType.EmployeeDb) && qt.order.addedToEmployeeHandle)) {
      LOG.trace("Order " + qt.order.id + ": Not queueing task since task already done..");
      return; 
    }
    ArrayList<Exception> list = queue.exceptionsList;
    Thread t = new Thread(new Runnable() {
      @Override
      public void run() {
        Retry.Operation op = (list) -> {
          if (!list.isEmpty()) {
            LOG.warn("Order " + qt.order.id + ": Error in connecting to queue db, trying again..");
            throw list.remove(0); 
          } 
          queue.add(qt); 
          queueItems++; 
          LOG.info("Order " + qt.order.id + ": " + qt.getType() + " task enqueued..");
          tryDoingTasksInQueue();
          return;  
        };
        Retry.HandleErrorIssue<QueueTask> handleError = (qt,err) -> {
          if (qt.taskType.equals(TaskType.Payment)) {
            qt.order.paid = PaymentStatus.NotDone; 
            sendPaymentFailureMessage(qt.order); 
            LOG.error("Order " + qt.order.id + ": Unable to enqueue payment task, payment failed..");
          }
          LOG.error("Order " + qt.order.id + ": Unable to enqueue task of type " + qt.getType() 
              + ", trying to add to employee handle..");
          employeeHandleIssue(qt.order);
          return; 
        };
        Retry<QueueTask> r = new Retry<QueueTask>(op, handleError, numOfRetries, retryDuration,
            e -> DatabaseUnavailableException.class.isAssignableFrom(e.getClass()));
        try {
          r.perform(list, qt);
        } catch (Exception e1) {
          e1.printStackTrace();
        } 
      } 
    });
    t.start();
  }

  private void tryDoingTasksInQueue() { //commander controls operations done to queue
    ArrayList<Exception> list = queue.exceptionsList;
    Thread t2 = new Thread(new Runnable() {
      @Override
      public void run() {
        Retry.Operation op = (list) -> {
          if (!list.isEmpty()) {
            LOG.warn("Error in accessing queue db to do tasks, trying again..");
            throw list.remove(0); 
          }
          doTasksInQueue();
          return; 
        };
        Retry.HandleErrorIssue<QueueTask> handleError = (o,err) -> {
          return; 
        };
        Retry<QueueTask> r = new Retry<QueueTask>(op, handleError, numOfRetries, retryDuration,
            e -> DatabaseUnavailableException.class.isAssignableFrom(e.getClass()));
        try {
          r.perform(list, null);
        } catch (Exception e1) {
          e1.printStackTrace();
        } 
      } 
    });
    t2.start();
  }

  private void tryDequeue() {
    ArrayList<Exception> list = queue.exceptionsList;
    Thread t3 = new Thread(new Runnable() {
      @Override
      public void run() {
        Retry.Operation op = (list) -> {
          if (!list.isEmpty()) {
            LOG.warn("Error in accessing queue db to dequeue task, trying again..");
            throw list.remove(0); 
          } 
          queue.dequeue(); 
          queueItems--; 
          return;
        };
        Retry.HandleErrorIssue<QueueTask> handleError = (o,err) -> {
          return; 
        };
        Retry<QueueTask> r = new Retry<QueueTask>(op, handleError, numOfRetries, retryDuration,
            e -> DatabaseUnavailableException.class.isAssignableFrom(e.getClass()));
        try {
          r.perform(list, null);
        } catch (Exception e1) {
          e1.printStackTrace();
        } 
      }
    });
    t3.start();
  }
  
  private void sendSuccessMessage(Order order) {
    if (System.currentTimeMillis() - order.createdTime >= this.messageTime) {
      LOG.trace("Order " + order.id + ": Message time for order over, returning..");
      return;
    }
    ArrayList<Exception> list = messagingService.exceptionsList;
    Thread t = new Thread(new Runnable() {
      @Override
      public void run() {
        Retry.Operation op = (l) -> {
          if (!l.isEmpty()) {
            if (DatabaseUnavailableException.class.isAssignableFrom(l.get(0).getClass())) {
              LOG.debug("Order " + order.id + ": Error in connecting to messaging service "
                  + "(Payment Success msg), trying again..");
            } else {
              LOG.debug("Order " + order.id + ": Error in creating Payment Success messaging request..");
            }
            throw l.remove(0); 
          } 
          if (!order.messageSent.equals(MessageSent.PaymentFail) 
              && !order.messageSent.equals(MessageSent.PaymentSuccessful)) {
            String requestId = messagingService.receiveRequest(2); 
            order.messageSent = MessageSent.PaymentSuccessful;
            LOG.info("Order " + order.id + ": Payment Success message sent, request Id: " + requestId);
          } 
          return; 
        };
        Retry.HandleErrorIssue<Order> handleError = (o,err) -> {
          try {
            if ((o.messageSent.equals(MessageSent.NoneSent) || o.messageSent.equals(MessageSent.PaymentTrying)) 
                && System.currentTimeMillis() - o.createdTime < messageTime) {
              QueueTask qt = new QueueTask(order, TaskType.Messaging,2);
              updateQueue(qt);
              LOG.info("Order " + order.id + ": Error in sending Payment Success message, trying to "
                  + "queue task and add to employee handle..");
              employeeHandleIssue(order);
            }
          } catch (InterruptedException e1) {
            e1.printStackTrace();
          } 
          return; 
        };
        Retry<Order> r = new Retry<Order>(op, handleError, numOfRetries, retryDuration,
            e -> DatabaseUnavailableException.class.isAssignableFrom(e.getClass()));
        try {
          r.perform(list, order);
        } catch (Exception e1) {
          e1.printStackTrace();
        } 
      } 
    });
    t.start();
  }

  private void sendPaymentFailureMessage(Order order) {
    if (System.currentTimeMillis() - order.createdTime >= this.messageTime) {
      LOG.trace("Order " + order.id + ": Message time for order over, returning..");
      return;
    }
    ArrayList<Exception> list = messagingService.exceptionsList;
    Thread t = new Thread(new Runnable() {
      @Override
      public void run() {
        Retry.Operation op = (l) -> {
          if (!l.isEmpty()) {
            if (DatabaseUnavailableException.class.isAssignableFrom(l.get(0).getClass())) {
              LOG.debug("Order " + order.id + ": Error in connecting to messaging service "
                  + "(Payment Failure msg), trying again..");
            } else {
              LOG.debug("Order " + order.id + ": Error in creating Payment Failure message request..");
            }
            throw l.remove(0); 
          } 
          if (!order.messageSent.equals(MessageSent.PaymentFail) 
              && !order.messageSent.equals(MessageSent.PaymentSuccessful)) { 
            String requestId = messagingService.receiveRequest(0); 
            order.messageSent = MessageSent.PaymentFail;
            LOG.info("Order " + order.id + ": Payment Failure message sent successfully, request Id: " + requestId);
          } 
          return; 
        };
        Retry.HandleErrorIssue<Order> handleError = (o,err) -> {
          if ((o.messageSent.equals(MessageSent.NoneSent) || o.messageSent.equals(MessageSent.PaymentTrying))
              && System.currentTimeMillis() - o.createdTime < messageTime) {
            try {
              QueueTask qt = new QueueTask(order, TaskType.Messaging,0);
              updateQueue(qt);
              LOG.warn("Order " + order.id + ": Error in sending Payment Failure message, "
                  + "trying to queue task and add to employee handle..");
              employeeHandleIssue(o);
            } catch (InterruptedException e1) {
              e1.printStackTrace();
            } 
            return; 
          } 
        };
        Retry<Order> r = new Retry<Order>(op, handleError, numOfRetries, retryDuration,
            e -> DatabaseUnavailableException.class.isAssignableFrom(e.getClass()));
        try {
          r.perform(list, order);
        } catch (Exception e1) {
          e1.printStackTrace();
        } 
      } 
    });
    t.start();
  }

  private void sendPaymentPossibleErrorMsg(Order order) {
    if (System.currentTimeMillis() - order.createdTime >= this.messageTime) {
      LOG.trace("Message time for order over, returning..");
      return;
    }
    ArrayList<Exception> list = messagingService.exceptionsList;
    Thread t = new Thread(new Runnable() {
      @Override
      public void run() {
        Retry.Operation op = (l) -> {
          if (!l.isEmpty()) {
            if (DatabaseUnavailableException.class.isAssignableFrom(l.get(0).getClass())) {
              LOG.debug("Order " + order.id + ": Error in connecting to messaging service "
                  + "(Payment Error msg), trying again..");
            } else {
              LOG.debug("Order " + order.id + ": Error in creating Payment Error messaging request..");
            }
            throw l.remove(0); 
          }
          if (order.paid.equals(PaymentStatus.Trying) && order.messageSent.equals(MessageSent.NoneSent)) {
            String requestId = messagingService.receiveRequest(1); 
            order.messageSent = MessageSent.PaymentTrying; 
            LOG.info("Order " + order.id + ": Payment Error message sent successfully, request Id: " + requestId);
          } 
          return; 
        };
        Retry.HandleErrorIssue<Order> handleError = (o,err) -> {
          try {
            if (o.messageSent.equals(MessageSent.NoneSent) && order.paid.equals(PaymentStatus.Trying)
                && System.currentTimeMillis() - o.createdTime < messageTime) {
              QueueTask qt = new QueueTask(order,TaskType.Messaging,1);
              updateQueue(qt);
              LOG.warn("Order " + order.id + ": Error in sending Payment Error message, "
                  + "trying to queue task and add to employee handle..");
              employeeHandleIssue(o);
            } 
          } catch (InterruptedException e1) {
            e1.printStackTrace();
          } 
          return; 
        };
        Retry<Order> r = new Retry<Order>(op, handleError, numOfRetries, retryDuration,
            e -> DatabaseUnavailableException.class.isAssignableFrom(e.getClass()));
        try {
          r.perform(list, order);
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
    });
    t.start();
  }

  private void employeeHandleIssue(Order order) {
    if (System.currentTimeMillis() - order.createdTime >= this.employeeTime) {
      LOG.trace("Order " + order.id + ": Employee handle time for order over, returning..");
      return;
    }
    ArrayList<Exception> list = employeeDb.exceptionsList;
    Thread t = new Thread(new Runnable() {
      @Override
      public void run() {
        Retry.Operation op = (l) -> {
          if (!l.isEmpty()) {
            LOG.warn("Order " + order.id + ": Error in connecting to employee handle, trying again..");
            throw l.remove(0); 
          } 
          if (!order.addedToEmployeeHandle) {
            employeeDb.receiveRequest(order); 
            order.addedToEmployeeHandle = true;
            LOG.info("Order " + order.id + ": Added order to employee database");
          }
          return; 
        };
        Retry.HandleErrorIssue<Order> handleError = (o,err) -> {
          try {
            if (!o.addedToEmployeeHandle && System.currentTimeMillis() - order.createdTime < employeeTime) {
              QueueTask qt = new QueueTask(order, TaskType.EmployeeDb,-1);
              updateQueue(qt);
              LOG.warn("Order " + order.id + ": Error in adding to employee db, trying to queue task..");
              return;
            } 
          } catch (InterruptedException e1) {
            e1.printStackTrace();
          }
          return; 
        };
        Retry<Order> r = new Retry<Order>(op, handleError, numOfRetries, retryDuration,
            e -> DatabaseUnavailableException.class.isAssignableFrom(e.getClass()));
        try {
          r.perform(list, order);
        } catch (Exception e1) {
          e1.printStackTrace();
        }
      }
    });
    t.start();
  }

  private void doTasksInQueue() throws Exception {
    if (queueItems != 0) {
      QueueTask qt = queue.peek(); //this should probably be cloned here
      //this is why we have retry for doTasksInQueue
      LOG.trace("Order " + qt.order.id + ": Started doing task of type " + qt.getType());
      if (qt.firstAttemptTime == -1) {
        qt.firstAttemptTime = System.currentTimeMillis();
      } 
      if (System.currentTimeMillis() - qt.firstAttemptTime >= queueTaskTime) {
        tryDequeue();
        LOG.trace("Order " + qt.order.id + ": This queue task of type " + qt.getType()
            + " does not need to be done anymore (timeout), dequeue..");
      } else {
        if (qt.taskType.equals(TaskType.Payment)) {
          if (!qt.order.paid.equals(PaymentStatus.Trying)) {
            tryDequeue();
            LOG.trace("Order " + qt.order.id + ": This payment task already done, dequeueing..");
          } else {
            sendPaymentRequest(qt.order);
            LOG.debug("Order " + qt.order.id + ": Trying to connect to payment service..");
          }
        } else if (qt.taskType.equals(TaskType.Messaging)) {
          if (qt.order.messageSent.equals(MessageSent.PaymentFail) 
              || qt.order.messageSent.equals(MessageSent.PaymentSuccessful)) {
            tryDequeue();
            LOG.trace("Order " + qt.order.id + ": This messaging task already done, dequeue..");
          } else if ((qt.messageType == 1 && (!qt.order.messageSent.equals(MessageSent.NoneSent) 
              || !qt.order.paid.equals(PaymentStatus.Trying)))) {
            tryDequeue();
            LOG.trace("Order " + qt.order.id + ": This messaging task does not need to be done, dequeue..");
          } else if (qt.messageType == 0) {
            sendPaymentFailureMessage(qt.order);
            LOG.debug("Order " + qt.order.id + ": Trying to connect to messaging service..");
          } else if (qt.messageType == 1) {
            sendPaymentPossibleErrorMsg(qt.order);
            LOG.debug("Order " + qt.order.id + ": Trying to connect to messaging service..");
          } else if (qt.messageType == 2) {
            sendSuccessMessage(qt.order);
            LOG.debug("Order " + qt.order.id + ": Trying to connect to messaging service..");
          }
        } else if (qt.taskType.equals(TaskType.EmployeeDb)) {
          if (qt.order.addedToEmployeeHandle) {
            tryDequeue();
            LOG.trace("Order " + qt.order.id + ": This employee handle task already done, dequeue..");
          } else {
            employeeHandleIssue(qt.order);
            LOG.debug("Order " + qt.order.id + ": Trying to connect to employee handle..");
          }
        }
      }
    }
    if (queueItems == 0) {
      LOG.trace("Queue is empty, returning..");
    } else {
      Thread.sleep(queueTaskTime / 3);
      tryDoingTasksInQueue();
    } 
    return; 
  }

}
