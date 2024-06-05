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
package com.iluwatar.commander;

import com.iluwatar.commander.Order.MessageSent;
import com.iluwatar.commander.Order.PaymentStatus;
import com.iluwatar.commander.employeehandle.EmployeeHandle;
import com.iluwatar.commander.exceptions.DatabaseUnavailableException;
import com.iluwatar.commander.exceptions.IsEmptyException;
import com.iluwatar.commander.exceptions.ItemUnavailableException;
import com.iluwatar.commander.exceptions.PaymentDetailsErrorException;
import com.iluwatar.commander.exceptions.ShippingNotPossibleException;
import com.iluwatar.commander.messagingservice.MessagingService;
import com.iluwatar.commander.paymentservice.PaymentService;
import com.iluwatar.commander.queue.QueueDatabase;
import com.iluwatar.commander.queue.QueueTask;
import com.iluwatar.commander.queue.QueueTask.TaskType;
import com.iluwatar.commander.shippingservice.ShippingService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <p>Commander pattern is used to handle all issues that can come up while making a
 * distributed transaction. The idea is to have a commander, which coordinates the execution of all
 * instructions and ensures proper completion using retries and taking care of idempotence. By
 * queueing instructions while they haven't been done, we can ensure a state of 'eventual
 * consistency'.</p>
 * <p>In our example, we have an e-commerce application. When the user places an order,
 * the shipping service is intimated first. If the service does not respond for some reason, the
 * order is not placed. If response is received, the commander then calls for the payment service to
 * be intimated. If this fails, the shipping still takes place (order converted to COD) and the item
 * is queued. If the queue is also found to be unavailable, the payment is noted to be not done and
 * this is added to an employee database. Three types of messages are sent to the user - one, if
 * payment succeeds; two, if payment fails definitively; and three, if payment fails in the first
 * attempt. If the message is not sent, this is also queued and is added to employee db. We also
 * have a time limit for each instruction to be completed, after which, the instruction is not
 * executed, thereby ensuring that resources are not held for too long. In the rare occasion in
 * which everything fails, an individual would have to step in to figure out how to solve the
 * issue.</p>
 * <p>We have abstract classes {@link Database} and {@link Service} which are extended
 * by all the databases and services. Each service has a database to be updated, and receives
 * request from an outside user (the {@link Commander} class here). There are 5 microservices -
 * {@link ShippingService}, {@link PaymentService}, {@link MessagingService}, {@link EmployeeHandle}
 * and a {@link QueueDatabase}. We use retries to execute any instruction using {@link Retry} class,
 * and idempotence is ensured by going through some checks before making requests to services and
 * making change in {@link Order} class fields if request succeeds or definitively fails. There is
 * a single class {@link AppAllCases} that looks at the different scenarios that may be encountered
 * during the placing of an order, including both success and failure cases for each service.</p>
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

  private static final Logger LOG = LoggerFactory.getLogger(Commander.class);
  //we could also have another db where it stores all orders

  private static final String ORDER_ID = "Order {}";
  private static final String REQUEST_ID = " request Id: {}";
  private static final String ERROR_CONNECTING_MSG_SVC =
          ": Error in connecting to messaging service ";
  private static final String TRY_CONNECTING_MSG_SVC =
          ": Trying to connect to messaging service..";

  private static final String DEFAULT_EXCEPTION_MESSAGE = "An exception occurred";

  Commander(EmployeeHandle empDb, PaymentService paymentService, ShippingService shippingService,
            MessagingService messagingService, QueueDatabase qdb, RetryParams retryParams, TimeLimits timeLimits) {
    this.paymentService = paymentService;
    this.shippingService = shippingService;
    this.messagingService = messagingService;
    this.employeeDb = empDb;
    this.queue = qdb;
    this.numOfRetries = retryParams.numOfRetries();
    this.retryDuration = retryParams.retryDuration();
    this.queueTime = timeLimits.queueTime();
    this.queueTaskTime = timeLimits.queueTaskTime();
    this.paymentTime = timeLimits.paymentTime();
    this.messageTime = timeLimits.messageTime();
    this.employeeTime = timeLimits.employeeTime();
    this.finalSiteMsgShown = false;
  }

  void placeOrder(Order order) {
    sendShippingRequest(order);
  }

  private void sendShippingRequest(Order order) {
    var list = shippingService.exceptionsList;
    Retry.Operation op = l -> {
      if (!l.isEmpty()) {
        if (DatabaseUnavailableException.class.isAssignableFrom(l.get(0).getClass())) {
          LOG.debug(ORDER_ID + ": Error in connecting to shipping service, "
              + "trying again..", order.id);
        } else {
          LOG.debug(ORDER_ID + ": Error in creating shipping request..", order.id);
        }
        throw l.remove(0);
      }
      String transactionId = shippingService.receiveRequest(order.item, order.user.address);
      //could save this transaction id in a db too
      LOG.info(ORDER_ID + ": Shipping placed successfully, transaction id: {}",
              order.id, transactionId);
      LOG.info("Order has been placed and will be shipped to you. Please wait while we make your"
          + " payment... ");
      sendPaymentRequest(order);
    };
    Retry.HandleErrorIssue<Order> handleError = (o, err) -> {
      if (ShippingNotPossibleException.class.isAssignableFrom(err.getClass())) {
        LOG.info("Shipping is currently not possible to your address. We are working on the problem"
            + " and will get back to you asap.");
        finalSiteMsgShown = true;
        LOG.info(ORDER_ID + ": Shipping not possible to address, trying to add problem "
            + "to employee db..", order.id);
        employeeHandleIssue(o);
      } else if (ItemUnavailableException.class.isAssignableFrom(err.getClass())) {
        LOG.info("This item is currently unavailable. We will inform you as soon as the item "
            + "becomes available again.");
        finalSiteMsgShown = true;
        LOG.info(ORDER_ID + ": Item {}" + " unavailable, trying to add "
            + "problem to employee handle..", order.id, order.item);
        employeeHandleIssue(o);
      } else {
        LOG.info("Sorry, there was a problem in creating your order. Please try later.");
        LOG.error(ORDER_ID + ": Shipping service unavailable, order not placed..", order.id);
        finalSiteMsgShown = true;
      }
    };
    var r = new Retry<>(op, handleError, numOfRetries, retryDuration,
        e -> DatabaseUnavailableException.class.isAssignableFrom(e.getClass()));
    r.perform(list, order);
  }

  private void sendPaymentRequest(Order order) {
    if (System.currentTimeMillis() - order.createdTime >= this.paymentTime) {
      if (order.paid.equals(PaymentStatus.TRYING)) {
        order.paid = PaymentStatus.NOT_DONE;
        sendPaymentFailureMessage(order);
        LOG.error(ORDER_ID + ": Payment time for order over, failed and returning..", order.id);
      } //if succeeded or failed, would have been dequeued, no attempt to make payment     
      return;
    }
    var list = paymentService.exceptionsList;
    var t = new Thread(() -> {
      Retry.Operation op = getRetryOperation(order);

      Retry.HandleErrorIssue<Order> handleError = getRetryHandleErrorIssue(order);

      var r = new Retry<>(op, handleError, numOfRetries, retryDuration,
          e -> DatabaseUnavailableException.class.isAssignableFrom(e.getClass()));
      try {
        r.perform(list, order);
      } catch (Exception e1) {
        LOG.error(DEFAULT_EXCEPTION_MESSAGE, e1);
      }
    });

    t.start();
  }

  private Retry.HandleErrorIssue<Order> getRetryHandleErrorIssue(Order order) {
    return (o, err) -> {
      if (PaymentDetailsErrorException.class.isAssignableFrom(err.getClass())) {
        handlePaymentDetailsError(order.id, o);
      } else {
        if (o.messageSent.equals(MessageSent.NONE_SENT)) {
          handlePaymentError(order.id, o);
        }
        if (o.paid.equals(PaymentStatus.TRYING) && System
            .currentTimeMillis() - o.createdTime < paymentTime) {
          var qt = new QueueTask(o, TaskType.PAYMENT, -1);
          updateQueue(qt);
        }
      }
    };
  }

  private void handlePaymentError(String orderId, Order o) {
    if (!finalSiteMsgShown) {
      LOG.info("There was an error in payment. We are on it, and will get back to you "
          + "asap. Don't worry, your order has been placed and will be shipped.");
      finalSiteMsgShown = true;
    }
    LOG.warn(ORDER_ID + ": Payment error, going to queue..", orderId);
    sendPaymentPossibleErrorMsg(o);
  }

  private void handlePaymentDetailsError(String orderId, Order o) {
    if (!finalSiteMsgShown) {
      LOG.info("There was an error in payment. Your account/card details "
          + "may have been incorrect. "
          + "Meanwhile, your order has been converted to COD and will be shipped.");
      finalSiteMsgShown = true;
    }
    LOG.error(ORDER_ID + ": Payment details incorrect, failed..", orderId);
    o.paid = PaymentStatus.NOT_DONE;
    sendPaymentFailureMessage(o);
  }

  private Retry.Operation getRetryOperation(Order order) {
    return l -> {
      if (!l.isEmpty()) {
        if (DatabaseUnavailableException.class.isAssignableFrom(l.get(0).getClass())) {
          LOG.debug(ORDER_ID + ": Error in connecting to payment service,"
              + " trying again..", order.id);
        } else {
          LOG.debug(ORDER_ID + ": Error in creating payment request..", order.id);
        }
        throw l.remove(0);
      }
      if (order.paid.equals(PaymentStatus.TRYING)) {
        var transactionId = paymentService.receiveRequest(order.price);
        order.paid = PaymentStatus.DONE;
        LOG.info(ORDER_ID + ": Payment successful, transaction Id: {}",
                order.id, transactionId);
        if (!finalSiteMsgShown) {
          LOG.info("Payment made successfully, thank you for shopping with us!!");
          finalSiteMsgShown = true;
        }
        sendSuccessMessage(order);
      }
    };
  }

  private void updateQueue(QueueTask qt) {
    if (System.currentTimeMillis() - qt.order.createdTime >= this.queueTime) {
      // since payment time is lesser than queuetime it would have already failed..
      // additional check not needed
      LOG.trace(ORDER_ID + ": Queue time for order over, failed..", qt.order.id);
      return;
    } else if (qt.taskType.equals(TaskType.PAYMENT) && !qt.order.paid.equals(PaymentStatus.TRYING)
        || qt.taskType.equals(TaskType.MESSAGING) && (qt.messageType == 1
        && !qt.order.messageSent.equals(MessageSent.NONE_SENT)
        || qt.order.messageSent.equals(MessageSent.PAYMENT_FAIL)
        || qt.order.messageSent.equals(MessageSent.PAYMENT_SUCCESSFUL))
        || qt.taskType.equals(TaskType.EMPLOYEE_DB) && qt.order.addedToEmployeeHandle) {
      LOG.trace(ORDER_ID + ": Not queueing task since task already done..", qt.order.id);
      return;
    }
    var list = queue.exceptionsList;
    Thread t = new Thread(() -> {
      Retry.Operation op = list1 -> {
        if (!list1.isEmpty()) {
          LOG.warn(ORDER_ID + ": Error in connecting to queue db, trying again..", qt.order.id);
          throw list1.remove(0);
        }
        queue.add(qt);
        queueItems++;
        LOG.info(ORDER_ID + ": {}" + " task enqueued..", qt.order.id, qt.getType());
        tryDoingTasksInQueue();
      };
      Retry.HandleErrorIssue<QueueTask> handleError = (qt1, err) -> {
        if (qt1.taskType.equals(TaskType.PAYMENT)) {
          qt1.order.paid = PaymentStatus.NOT_DONE;
          sendPaymentFailureMessage(qt1.order);
          LOG.error(ORDER_ID + ": Unable to enqueue payment task,"
              + " payment failed..", qt1.order.id);
        }
        LOG.error(ORDER_ID + ": Unable to enqueue task of type {}"
                + ", trying to add to employee handle..", qt1.order.id, qt1.getType());
        employeeHandleIssue(qt1.order);
      };
      var r = new Retry<>(op, handleError, numOfRetries, retryDuration,
          e -> DatabaseUnavailableException.class.isAssignableFrom(e.getClass()));
      try {
        r.perform(list, qt);
      } catch (Exception e1) {
        LOG.error(DEFAULT_EXCEPTION_MESSAGE, e1);
      }
    });
    t.start();
  }

  private void tryDoingTasksInQueue() { //commander controls operations done to queue
    var list = queue.exceptionsList;
    var t2 = new Thread(() -> {
      Retry.Operation op = list1 -> {
        if (!list1.isEmpty()) {
          LOG.warn("Error in accessing queue db to do tasks, trying again..");
          throw list1.remove(0);
        }
        doTasksInQueue();
      };
      Retry.HandleErrorIssue<QueueTask> handleError = (o, err) -> {
      };
      var r = new Retry<>(op, handleError, numOfRetries, retryDuration,
          e -> DatabaseUnavailableException.class.isAssignableFrom(e.getClass()));
      try {
        r.perform(list, null);
      } catch (Exception e1) {
        LOG.error(DEFAULT_EXCEPTION_MESSAGE, e1);
      }
    });
    t2.start();
  }

  private void tryDequeue() {
    var list = queue.exceptionsList;
    var t3 = new Thread(() -> {
      Retry.Operation op = list1 -> {
        if (!list1.isEmpty()) {
          LOG.warn("Error in accessing queue db to dequeue task, trying again..");
          throw list1.remove(0);
        }
        queue.dequeue();
        queueItems--;
      };
      Retry.HandleErrorIssue<QueueTask> handleError = (o, err) -> {
      };
      var r = new Retry<>(op, handleError, numOfRetries, retryDuration,
          e -> DatabaseUnavailableException.class.isAssignableFrom(e.getClass()));
      try {
        r.perform(list, null);
      } catch (Exception e1) {
        LOG.error(DEFAULT_EXCEPTION_MESSAGE, e1);
      }
    });
    t3.start();
  }

  private void sendSuccessMessage(Order order) {
    if (System.currentTimeMillis() - order.createdTime >= this.messageTime) {
      LOG.trace(ORDER_ID + ": Message time for order over, returning..", order.id);
      return;
    }
    var list = messagingService.exceptionsList;
    Thread t = new Thread(() -> {
      Retry.Operation op = handleSuccessMessageRetryOperation(order);
      Retry.HandleErrorIssue<Order> handleError = (o, err) -> handleSuccessMessageErrorIssue(order, o);
      var r = new Retry<>(op, handleError, numOfRetries, retryDuration,
          e -> DatabaseUnavailableException.class.isAssignableFrom(e.getClass()));
      try {
        r.perform(list, order);
      } catch (Exception e1) {
        LOG.error(DEFAULT_EXCEPTION_MESSAGE, e1);
      }
    });
    t.start();
  }

  private void handleSuccessMessageErrorIssue(Order order, Order o) {
    if ((o.messageSent.equals(MessageSent.NONE_SENT) || o.messageSent
        .equals(MessageSent.PAYMENT_TRYING))
        && System.currentTimeMillis() - o.createdTime < messageTime) {
      var qt = new QueueTask(order, TaskType.MESSAGING, 2);
      updateQueue(qt);
      LOG.info(ORDER_ID + ": Error in sending Payment Success message, trying to"
          + " queue task and add to employee handle..", order.id);
      employeeHandleIssue(order);
    }
  }

  private Retry.Operation handleSuccessMessageRetryOperation(Order order) {
    return l -> {
      if (!l.isEmpty()) {
        if (DatabaseUnavailableException.class.isAssignableFrom(l.get(0).getClass())) {
          LOG.debug(ORDER_ID + ERROR_CONNECTING_MSG_SVC
              + "(Payment Success msg), trying again..", order.id);
        } else {
          LOG.debug(ORDER_ID + ": Error in creating Payment Success"
              + " messaging request..", order.id);
        }
        throw l.remove(0);
      }
      if (!order.messageSent.equals(MessageSent.PAYMENT_FAIL)
          && !order.messageSent.equals(MessageSent.PAYMENT_SUCCESSFUL)) {
        var requestId = messagingService.receiveRequest(2);
        order.messageSent = MessageSent.PAYMENT_SUCCESSFUL;
        LOG.info(ORDER_ID + ": Payment Success message sent,"
            + REQUEST_ID, order.id, requestId);
      }
    };
  }

  private void sendPaymentFailureMessage(Order order) {
    if (System.currentTimeMillis() - order.createdTime >= this.messageTime) {
      LOG.trace(ORDER_ID + ": Message time for order over, returning..", order.id);
      return;
    }
    var list = messagingService.exceptionsList;
    var t = new Thread(() -> {
      Retry.Operation op = l -> handlePaymentFailureRetryOperation(order, l);
      Retry.HandleErrorIssue<Order> handleError = (o, err) -> handlePaymentErrorIssue(order, o);
      var r = new Retry<>(op, handleError, numOfRetries, retryDuration,
          e -> DatabaseUnavailableException.class.isAssignableFrom(e.getClass()));
      try {
        r.perform(list, order);
      } catch (Exception e1) {
        LOG.error(DEFAULT_EXCEPTION_MESSAGE, e1);
      }
    });
    t.start();
  }

  private void handlePaymentErrorIssue(Order order, Order o) {
    if ((o.messageSent.equals(MessageSent.NONE_SENT) || o.messageSent
        .equals(MessageSent.PAYMENT_TRYING))
        && System.currentTimeMillis() - o.createdTime < messageTime) {
      var qt = new QueueTask(order, TaskType.MESSAGING, 0);
      updateQueue(qt);
      LOG.warn(ORDER_ID + ": Error in sending Payment Failure message, "
              + "trying to queue task and add to employee handle..", order.id);
      employeeHandleIssue(o);
    }
  }

  private void handlePaymentFailureRetryOperation(Order order, List<Exception> l) throws IndexOutOfBoundsException, DatabaseUnavailableException {
    if (!l.isEmpty()) {
      if (DatabaseUnavailableException.class.isAssignableFrom(l.get(0).getClass())) {
        LOG.debug(ORDER_ID + ERROR_CONNECTING_MSG_SVC + "(Payment Failure msg), trying again..", order.id);
      } else {
        LOG.debug(ORDER_ID + ": Error in creating Payment Failure" + " message request..", order.id);
      }
      throw new IndexOutOfBoundsException();
    }
    if (!order.messageSent.equals(MessageSent.PAYMENT_FAIL)
        && !order.messageSent.equals(MessageSent.PAYMENT_SUCCESSFUL)) {
      var requestId = messagingService.receiveRequest(0);
      order.messageSent = MessageSent.PAYMENT_FAIL;
      LOG.info(ORDER_ID + ": Payment Failure message sent successfully,"
          + REQUEST_ID, order.id, requestId);
    }
  }

  private void sendPaymentPossibleErrorMsg(Order order) {
    if (System.currentTimeMillis() - order.createdTime >= this.messageTime) {
      LOG.trace("Message time for order over, returning..");
      return;
    }
    var list = messagingService.exceptionsList;
    var t = new Thread(() -> {
      Retry.Operation op = l -> handlePaymentPossibleErrorMsgRetryOperation(order, l);
      Retry.HandleErrorIssue<Order> handleError = (o, err) -> handlePaymentPossibleErrorMsgErrorIssue(order, o);
      var r = new Retry<>(op, handleError, numOfRetries, retryDuration,
          e -> DatabaseUnavailableException.class.isAssignableFrom(e.getClass()));
      try {
        r.perform(list, order);
      } catch (Exception e1) {
        LOG.error(DEFAULT_EXCEPTION_MESSAGE, e1);
      }
    });
    t.start();
  }

  private void handlePaymentPossibleErrorMsgErrorIssue(Order order, Order o) {
    if (o.messageSent.equals(MessageSent.NONE_SENT) && order.paid
        .equals(PaymentStatus.TRYING)
        && System.currentTimeMillis() - o.createdTime < messageTime) {
      var qt = new QueueTask(order, TaskType.MESSAGING, 1);
      updateQueue(qt);
      LOG.warn("Order {}: Error in sending Payment Error message, trying to queue task and add to employee handle..",
              order.id);
      employeeHandleIssue(o);
    }
  }

  private void handlePaymentPossibleErrorMsgRetryOperation(Order order, List<Exception> l)
      throws IndexOutOfBoundsException, DatabaseUnavailableException {
    if (!l.isEmpty()) {
      if (DatabaseUnavailableException.class.isAssignableFrom(l.get(0).getClass())) {
        LOG.debug(ORDER_ID + ERROR_CONNECTING_MSG_SVC
            + "(Payment Error msg), trying again..", order.id);
      } else {
        LOG.debug(ORDER_ID + ": Error in creating Payment Error"
            + " messaging request..", order.id);
      }
      throw new IndexOutOfBoundsException();
    }
    if (order.paid.equals(PaymentStatus.TRYING) && order.messageSent
        .equals(MessageSent.NONE_SENT)) {
      var requestId = messagingService.receiveRequest(1);
      order.messageSent = MessageSent.PAYMENT_TRYING;
      LOG.info(ORDER_ID + ": Payment Error message sent successfully,"
          + REQUEST_ID, order.id, requestId);
    }
  }

  private void employeeHandleIssue(Order order) {
    if (System.currentTimeMillis() - order.createdTime >= this.employeeTime) {
      LOG.trace(ORDER_ID + ": Employee handle time for order over, returning..", order.id);
      return;
    }
    var list = employeeDb.exceptionsList;
    var t = new Thread(() -> {
      Retry.Operation op = l -> {
        if (!l.isEmpty()) {
          LOG.warn(ORDER_ID + ": Error in connecting to employee handle,"
              + " trying again..", order.id);
          throw l.remove(0);
        }
        if (!order.addedToEmployeeHandle) {
          employeeDb.receiveRequest(order);
          order.addedToEmployeeHandle = true;
          LOG.info(ORDER_ID + ": Added order to employee database", order.id);
        }
      };
      Retry.HandleErrorIssue<Order> handleError = (o, err) -> {
        if (!o.addedToEmployeeHandle && System
            .currentTimeMillis() - order.createdTime < employeeTime) {
          var qt = new QueueTask(order, TaskType.EMPLOYEE_DB, -1);
          updateQueue(qt);
          LOG.warn(ORDER_ID + ": Error in adding to employee db,"
              + " trying to queue task..", order.id);
        }
      };
      var r = new Retry<>(op, handleError, numOfRetries, retryDuration,
          e -> DatabaseUnavailableException.class.isAssignableFrom(e.getClass()));
      try {
        r.perform(list, order);
      } catch (Exception e1) {
        LOG.error(DEFAULT_EXCEPTION_MESSAGE, e1);
      }
    });
    t.start();
  }

  private void doTasksInQueue() throws IsEmptyException, InterruptedException {
    if (queueItems != 0) {
      var qt = queue.peek(); //this should probably be cloned here
      //this is why we have retry for doTasksInQueue
      LOG.trace(ORDER_ID + ": Started doing task of type {}", qt.order.id, qt.getType());
      if (qt.isFirstAttempt()) {
        qt.setFirstAttemptTime(System.currentTimeMillis());
      }
      if (System.currentTimeMillis() - qt.getFirstAttemptTime() >= queueTaskTime) {
        tryDequeue();
        LOG.trace(ORDER_ID + ": This queue task of type {}"
            + " does not need to be done anymore (timeout), dequeue..", qt.order.id, qt.getType());
      } else {
        switch (qt.taskType) {
          case PAYMENT -> doPaymentTask(qt);
          case MESSAGING -> doMessagingTask(qt);
          case EMPLOYEE_DB -> doEmployeeDbTask(qt);
          default -> throw new IllegalArgumentException("Unknown task type");
        }
      }
    }
    if (queueItems == 0) {
      LOG.trace("Queue is empty, returning..");
    } else {
      Thread.sleep(queueTaskTime / 3);
      tryDoingTasksInQueue();
    }
  }

  private void doEmployeeDbTask(QueueTask qt) {
    if (qt.order.addedToEmployeeHandle) {
      tryDequeue();
      LOG.trace(ORDER_ID + ": This employee handle task already done,"
          + " dequeue..", qt.order.id);
    } else {
      employeeHandleIssue(qt.order);
      LOG.debug(ORDER_ID + ": Trying to connect to employee handle..", qt.order.id);
    }
  }

  private void doMessagingTask(QueueTask qt) {
    if (qt.order.messageSent.equals(MessageSent.PAYMENT_FAIL)
        || qt.order.messageSent.equals(MessageSent.PAYMENT_SUCCESSFUL)) {
      tryDequeue();
      LOG.trace(ORDER_ID + ": This messaging task already done, dequeue..", qt.order.id);
    } else if (qt.messageType == 1 && (!qt.order.messageSent.equals(MessageSent.NONE_SENT)
        || !qt.order.paid.equals(PaymentStatus.TRYING))) {
      tryDequeue();
      LOG.trace(ORDER_ID + ": This messaging task does not need to be done,"
          + " dequeue..", qt.order.id);
    } else if (qt.messageType == 0) {
      sendPaymentFailureMessage(qt.order);
      LOG.debug(ORDER_ID + TRY_CONNECTING_MSG_SVC, qt.order.id);
    } else if (qt.messageType == 1) {
      sendPaymentPossibleErrorMsg(qt.order);
      LOG.debug(ORDER_ID + TRY_CONNECTING_MSG_SVC, qt.order.id);
    } else if (qt.messageType == 2) {
      sendSuccessMessage(qt.order);
      LOG.debug(ORDER_ID + TRY_CONNECTING_MSG_SVC, qt.order.id);
    }
  }

  private void doPaymentTask(QueueTask qt) {
    if (!qt.order.paid.equals(PaymentStatus.TRYING)) {
      tryDequeue();
      LOG.trace(ORDER_ID + ": This payment task already done, dequeueing..", qt.order.id);
    } else {
      sendPaymentRequest(qt.order);
      LOG.debug(ORDER_ID + ": Trying to connect to payment service..", qt.order.id);
    }
  }

}