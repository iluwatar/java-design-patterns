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

// ABOUTME: Commander pattern implementation for handling distributed transactions.
// ABOUTME: Coordinates execution of instructions ensuring eventual consistency via retries and idempotence.
package com.iluwatar.commander

import com.iluwatar.commander.employeehandle.EmployeeHandle
import com.iluwatar.commander.exceptions.DatabaseUnavailableException
import com.iluwatar.commander.exceptions.ItemUnavailableException
import com.iluwatar.commander.exceptions.PaymentDetailsErrorException
import com.iluwatar.commander.exceptions.ShippingNotPossibleException
import com.iluwatar.commander.messagingservice.MessagingService
import com.iluwatar.commander.paymentservice.PaymentService
import com.iluwatar.commander.queue.QueueDatabase
import com.iluwatar.commander.queue.QueueTask
import com.iluwatar.commander.shippingservice.ShippingService
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

class Commander(
    private val employeeDb: EmployeeHandle,
    private val paymentService: PaymentService,
    private val shippingService: ShippingService,
    private val messagingService: MessagingService,
    private val queue: QueueDatabase?,
    retryParams: RetryParams,
    timeLimits: TimeLimits,
) {
    private var queueItems = 0
    private val numOfRetries = retryParams.numOfRetries
    private val retryDuration = retryParams.retryDuration
    private val queueTime = timeLimits.queueTime
    private val queueTaskTime = timeLimits.queueTaskTime
    private val paymentTime = timeLimits.paymentTime
    private val messageTime = timeLimits.messageTime
    private val employeeTime = timeLimits.employeeTime
    private var finalSiteMsgShown = false

    companion object {
        private const val ORDER_ID = "Order {}"
        private const val REQUEST_ID = " request Id: {}"
        private const val ERROR_CONNECTING_MSG_SVC = ": Error in connecting to messaging service "
        private const val TRY_CONNECTING_MSG_SVC = ": Trying to connect to messaging service.."
        private const val DEFAULT_EXCEPTION_MESSAGE = "An exception occurred"
    }

    fun placeOrder(order: Order) {
        sendShippingRequest(order)
    }

    private fun sendShippingRequest(order: Order) {
        val list = shippingService.exceptionsList
        val op =
            Retry.Operation { l ->
                if (l.isNotEmpty()) {
                    if (DatabaseUnavailableException::class.java.isAssignableFrom(l[0].javaClass)) {
                        logger.debug { "Order ${order.id}: Error in connecting to shipping service, trying again.." }
                    } else {
                        logger.debug { "Order ${order.id}: Error in creating shipping request.." }
                    }
                    throw l.removeAt(0)
                }
                val transactionId = shippingService.receiveRequest(order.item, order.user.address)
                logger.info { "Order ${order.id}: Shipping placed successfully, transaction id: $transactionId" }
                logger.info { "Order has been placed and will be shipped to you. Please wait while we make your payment... " }
                sendPaymentRequest(order)
            }
        val handleError =
            Retry.HandleErrorIssue<Order> { o, err ->
                when {
                    ShippingNotPossibleException::class.java.isAssignableFrom(err.javaClass) -> {
                        logger.info { "Shipping is currently not possible to your address. We are working on the problem and will get back to you asap." }
                        finalSiteMsgShown = true
                        logger.info { "Order ${order.id}: Shipping not possible to address, trying to add problem to employee db.." }
                        o?.let { employeeHandleIssue(it) }
                    }
                    ItemUnavailableException::class.java.isAssignableFrom(err.javaClass) -> {
                        logger.info { "This item is currently unavailable. We will inform you as soon as the item becomes available again." }
                        finalSiteMsgShown = true
                        logger.info { "Order ${order.id}: Item ${order.item} unavailable, trying to add problem to employee handle.." }
                        o?.let { employeeHandleIssue(it) }
                    }
                    else -> {
                        logger.info { "Sorry, there was a problem in creating your order. Please try later." }
                        logger.error { "Order ${order.id}: Shipping service unavailable, order not placed.." }
                        finalSiteMsgShown = true
                    }
                }
            }
        val r =
            Retry(
                op,
                handleError,
                numOfRetries,
                retryDuration,
                { e -> DatabaseUnavailableException::class.java.isAssignableFrom(e.javaClass) },
            )
        r.perform(list, order)
    }

    private fun sendPaymentRequest(order: Order) {
        if (System.currentTimeMillis() - order.createdTime >= this.paymentTime) {
            if (order.paid == Order.PaymentStatus.TRYING) {
                order.paid = Order.PaymentStatus.NOT_DONE
                sendPaymentFailureMessage(order)
                logger.error { "Order ${order.id}: Payment time for order over, failed and returning.." }
            }
            return
        }
        val list = paymentService.exceptionsList
        val t =
            Thread {
                val op = getRetryOperation(order)
                val handleError = getRetryHandleErrorIssue(order)
                val r =
                    Retry(
                        op,
                        handleError,
                        numOfRetries,
                        retryDuration,
                        { e -> DatabaseUnavailableException::class.java.isAssignableFrom(e.javaClass) },
                    )
                try {
                    r.perform(list, order)
                } catch (e1: Exception) {
                    logger.error(e1) { DEFAULT_EXCEPTION_MESSAGE }
                }
            }
        t.start()
    }

    private fun getRetryHandleErrorIssue(order: Order): Retry.HandleErrorIssue<Order> {
        return Retry.HandleErrorIssue { o, err ->
            if (o == null) return@HandleErrorIssue
            if (PaymentDetailsErrorException::class.java.isAssignableFrom(err.javaClass)) {
                handlePaymentDetailsError(order.id, o)
            } else {
                if (o.messageSent == Order.MessageSent.NONE_SENT) {
                    handlePaymentError(order.id, o)
                }
                if (o.paid == Order.PaymentStatus.TRYING &&
                    System.currentTimeMillis() - o.createdTime < paymentTime
                ) {
                    val qt = QueueTask(o, QueueTask.TaskType.PAYMENT, -1)
                    updateQueue(qt)
                }
            }
        }
    }

    private fun handlePaymentError(
        orderId: String,
        o: Order,
    ) {
        if (!finalSiteMsgShown) {
            logger.info { "There was an error in payment. We are on it, and will get back to you asap. Don't worry, your order has been placed and will be shipped." }
            finalSiteMsgShown = true
        }
        logger.warn { "Order $orderId: Payment error, going to queue.." }
        sendPaymentPossibleErrorMsg(o)
    }

    private fun handlePaymentDetailsError(
        orderId: String,
        o: Order,
    ) {
        if (!finalSiteMsgShown) {
            logger.info { "There was an error in payment. Your account/card details may have been incorrect. Meanwhile, your order has been converted to COD and will be shipped." }
            finalSiteMsgShown = true
        }
        logger.error { "Order $orderId: Payment details incorrect, failed.." }
        o.paid = Order.PaymentStatus.NOT_DONE
        sendPaymentFailureMessage(o)
    }

    private fun getRetryOperation(order: Order): Retry.Operation =
        Retry.Operation { l ->
            if (l.isNotEmpty()) {
                if (DatabaseUnavailableException::class.java.isAssignableFrom(l[0].javaClass)) {
                    logger.debug { "Order ${order.id}: Error in connecting to payment service, trying again.." }
                } else {
                    logger.debug { "Order ${order.id}: Error in creating payment request.." }
                }
                throw l.removeAt(0)
            }
            if (order.paid == Order.PaymentStatus.TRYING) {
                val transactionId = paymentService.receiveRequest(order.price)
                order.paid = Order.PaymentStatus.DONE
                logger.info { "Order ${order.id}: Payment successful, transaction Id: $transactionId" }
                if (!finalSiteMsgShown) {
                    logger.info { "Payment made successfully, thank you for shopping with us!!" }
                    finalSiteMsgShown = true
                }
                sendSuccessMessage(order)
            }
        }

    private fun updateQueue(qt: QueueTask) {
        if (System.currentTimeMillis() - qt.order.createdTime >= this.queueTime) {
            logger.trace { "Order ${qt.order.id}: Queue time for order over, failed.." }
            return
        } else if ((qt.taskType == QueueTask.TaskType.PAYMENT && qt.order.paid != Order.PaymentStatus.TRYING) ||
            (
                qt.taskType == QueueTask.TaskType.MESSAGING &&
                    (
                        (qt.messageType == 1 && qt.order.messageSent != Order.MessageSent.NONE_SENT) ||
                            qt.order.messageSent == Order.MessageSent.PAYMENT_FAIL ||
                            qt.order.messageSent == Order.MessageSent.PAYMENT_SUCCESSFUL
                    )
            ) ||
            (qt.taskType == QueueTask.TaskType.EMPLOYEE_DB && qt.order.addedToEmployeeHandle)
        ) {
            logger.trace { "Order ${qt.order.id}: Not queueing task since task already done.." }
            return
        }
        val list = queue?.exceptionsList ?: return
        val t =
            Thread {
                val op =
                    Retry.Operation { list1 ->
                        if (list1.isNotEmpty()) {
                            logger.warn { "Order ${qt.order.id}: Error in connecting to queue db, trying again.." }
                            throw list1.removeAt(0)
                        }
                        queue.add(qt)
                        queueItems++
                        logger.info { "Order ${qt.order.id}: ${qt.getType()} task enqueued.." }
                        tryDoingTasksInQueue()
                    }
                val handleError =
                    Retry.HandleErrorIssue<QueueTask> { qt1, _ ->
                        if (qt1 == null) return@HandleErrorIssue
                        if (qt1.taskType == QueueTask.TaskType.PAYMENT) {
                            qt1.order.paid = Order.PaymentStatus.NOT_DONE
                            sendPaymentFailureMessage(qt1.order)
                            logger.error { "Order ${qt1.order.id}: Unable to enqueue payment task, payment failed.." }
                        }
                        logger.error { "Order ${qt1.order.id}: Unable to enqueue task of type ${qt1.getType()}, trying to add to employee handle.." }
                        employeeHandleIssue(qt1.order)
                    }
                val r =
                    Retry(
                        op,
                        handleError,
                        numOfRetries,
                        retryDuration,
                        { e -> DatabaseUnavailableException::class.java.isAssignableFrom(e.javaClass) },
                    )
                try {
                    r.perform(list, qt)
                } catch (e1: Exception) {
                    logger.error(e1) { DEFAULT_EXCEPTION_MESSAGE }
                }
            }
        t.start()
    }

    private fun tryDoingTasksInQueue() {
        val list = queue?.exceptionsList ?: return
        val t2 =
            Thread {
                val op =
                    Retry.Operation { list1 ->
                        if (list1.isNotEmpty()) {
                            logger.warn { "Error in accessing queue db to do tasks, trying again.." }
                            throw list1.removeAt(0)
                        }
                        doTasksInQueue()
                    }
                val handleError = Retry.HandleErrorIssue<QueueTask> { _, _ -> }
                val r =
                    Retry(
                        op,
                        handleError,
                        numOfRetries,
                        retryDuration,
                        { e -> DatabaseUnavailableException::class.java.isAssignableFrom(e.javaClass) },
                    )
                try {
                    r.perform(list, null)
                } catch (e1: Exception) {
                    logger.error(e1) { DEFAULT_EXCEPTION_MESSAGE }
                }
            }
        t2.start()
    }

    private fun tryDequeue() {
        val list = queue?.exceptionsList ?: return
        val t3 =
            Thread {
                val op =
                    Retry.Operation { list1 ->
                        if (list1.isNotEmpty()) {
                            logger.warn { "Error in accessing queue db to dequeue task, trying again.." }
                            throw list1.removeAt(0)
                        }
                        queue.dequeue()
                        queueItems--
                    }
                val handleError = Retry.HandleErrorIssue<QueueTask> { _, _ -> }
                val r =
                    Retry(
                        op,
                        handleError,
                        numOfRetries,
                        retryDuration,
                        { e -> DatabaseUnavailableException::class.java.isAssignableFrom(e.javaClass) },
                    )
                try {
                    r.perform(list, null)
                } catch (e1: Exception) {
                    logger.error(e1) { DEFAULT_EXCEPTION_MESSAGE }
                }
            }
        t3.start()
    }

    private fun sendSuccessMessage(order: Order) {
        if (System.currentTimeMillis() - order.createdTime >= this.messageTime) {
            logger.trace { "Order ${order.id}: Message time for order over, returning.." }
            return
        }
        val list = messagingService.exceptionsList
        val t =
            Thread {
                val op = handleSuccessMessageRetryOperation(order)
                val handleError =
                    Retry.HandleErrorIssue<Order> { o, _ ->
                        o?.let { handleSuccessMessageErrorIssue(order, it) }
                    }
                val r =
                    Retry(
                        op,
                        handleError,
                        numOfRetries,
                        retryDuration,
                        { e -> DatabaseUnavailableException::class.java.isAssignableFrom(e.javaClass) },
                    )
                try {
                    r.perform(list, order)
                } catch (e1: Exception) {
                    logger.error(e1) { DEFAULT_EXCEPTION_MESSAGE }
                }
            }
        t.start()
    }

    private fun handleSuccessMessageErrorIssue(
        order: Order,
        o: Order,
    ) {
        if ((
                o.messageSent == Order.MessageSent.NONE_SENT ||
                    o.messageSent == Order.MessageSent.PAYMENT_TRYING
            ) &&
            System.currentTimeMillis() - o.createdTime < messageTime
        ) {
            val qt = QueueTask(order, QueueTask.TaskType.MESSAGING, 2)
            updateQueue(qt)
            logger.info { "Order ${order.id}: Error in sending Payment Success message, trying to queue task and add to employee handle.." }
            employeeHandleIssue(order)
        }
    }

    private fun handleSuccessMessageRetryOperation(order: Order): Retry.Operation =
        Retry.Operation { l ->
            if (l.isNotEmpty()) {
                if (DatabaseUnavailableException::class.java.isAssignableFrom(l[0].javaClass)) {
                    logger.debug { "Order ${order.id}$ERROR_CONNECTING_MSG_SVC(Payment Success msg), trying again.." }
                } else {
                    logger.debug { "Order ${order.id}: Error in creating Payment Success messaging request.." }
                }
                throw l.removeAt(0)
            }
            if (order.messageSent != Order.MessageSent.PAYMENT_FAIL &&
                order.messageSent != Order.MessageSent.PAYMENT_SUCCESSFUL
            ) {
                val requestId = messagingService.receiveRequest(2)
                order.messageSent = Order.MessageSent.PAYMENT_SUCCESSFUL
                logger.info { "Order ${order.id}: Payment Success message sent,$REQUEST_ID $requestId" }
            }
        }

    private fun sendPaymentFailureMessage(order: Order) {
        if (System.currentTimeMillis() - order.createdTime >= this.messageTime) {
            logger.trace { "Order ${order.id}: Message time for order over, returning.." }
            return
        }
        val list = messagingService.exceptionsList
        val t =
            Thread {
                val op = Retry.Operation { l -> handlePaymentFailureRetryOperation(order, l) }
                val handleError =
                    Retry.HandleErrorIssue<Order> { o, _ ->
                        o?.let { handlePaymentErrorIssue(order, it) }
                    }
                val r =
                    Retry(
                        op,
                        handleError,
                        numOfRetries,
                        retryDuration,
                        { e -> DatabaseUnavailableException::class.java.isAssignableFrom(e.javaClass) },
                    )
                try {
                    r.perform(list, order)
                } catch (e1: Exception) {
                    logger.error(e1) { DEFAULT_EXCEPTION_MESSAGE }
                }
            }
        t.start()
    }

    private fun handlePaymentErrorIssue(
        order: Order,
        o: Order,
    ) {
        if ((
                o.messageSent == Order.MessageSent.NONE_SENT ||
                    o.messageSent == Order.MessageSent.PAYMENT_TRYING
            ) &&
            System.currentTimeMillis() - o.createdTime < messageTime
        ) {
            val qt = QueueTask(order, QueueTask.TaskType.MESSAGING, 0)
            updateQueue(qt)
            logger.warn { "Order ${order.id}: Error in sending Payment Failure message, trying to queue task and add to employee handle.." }
            employeeHandleIssue(o)
        }
    }

    private fun handlePaymentFailureRetryOperation(
        order: Order,
        l: MutableList<Exception>,
    ) {
        if (l.isNotEmpty()) {
            if (DatabaseUnavailableException::class.java.isAssignableFrom(l[0].javaClass)) {
                logger.debug { "Order ${order.id}$ERROR_CONNECTING_MSG_SVC(Payment Failure msg), trying again.." }
            } else {
                logger.debug { "Order ${order.id}: Error in creating Payment Failure message request.." }
            }
            throw IndexOutOfBoundsException()
        }
        if (order.messageSent != Order.MessageSent.PAYMENT_FAIL &&
            order.messageSent != Order.MessageSent.PAYMENT_SUCCESSFUL
        ) {
            val requestId = messagingService.receiveRequest(0)
            order.messageSent = Order.MessageSent.PAYMENT_FAIL
            logger.info { "Order ${order.id}: Payment Failure message sent successfully,$REQUEST_ID $requestId" }
        }
    }

    private fun sendPaymentPossibleErrorMsg(order: Order) {
        if (System.currentTimeMillis() - order.createdTime >= this.messageTime) {
            logger.trace { "Message time for order over, returning.." }
            return
        }
        val list = messagingService.exceptionsList
        val t =
            Thread {
                val op = Retry.Operation { l -> handlePaymentPossibleErrorMsgRetryOperation(order, l) }
                val handleError =
                    Retry.HandleErrorIssue<Order> { o, _ ->
                        o?.let { handlePaymentPossibleErrorMsgErrorIssue(order, it) }
                    }
                val r =
                    Retry(
                        op,
                        handleError,
                        numOfRetries,
                        retryDuration,
                        { e -> DatabaseUnavailableException::class.java.isAssignableFrom(e.javaClass) },
                    )
                try {
                    r.perform(list, order)
                } catch (e1: Exception) {
                    logger.error(e1) { DEFAULT_EXCEPTION_MESSAGE }
                }
            }
        t.start()
    }

    private fun handlePaymentPossibleErrorMsgErrorIssue(
        order: Order,
        o: Order,
    ) {
        if (o.messageSent == Order.MessageSent.NONE_SENT &&
            order.paid == Order.PaymentStatus.TRYING &&
            System.currentTimeMillis() - o.createdTime < messageTime
        ) {
            val qt = QueueTask(order, QueueTask.TaskType.MESSAGING, 1)
            updateQueue(qt)
            logger.warn { "Order ${order.id}: Error in sending Payment Error message, trying to queue task and add to employee handle.." }
            employeeHandleIssue(o)
        }
    }

    private fun handlePaymentPossibleErrorMsgRetryOperation(
        order: Order,
        l: MutableList<Exception>,
    ) {
        if (l.isNotEmpty()) {
            if (DatabaseUnavailableException::class.java.isAssignableFrom(l[0].javaClass)) {
                logger.debug { "Order ${order.id}$ERROR_CONNECTING_MSG_SVC(Payment Error msg), trying again.." }
            } else {
                logger.debug { "Order ${order.id}: Error in creating Payment Error messaging request.." }
            }
            throw IndexOutOfBoundsException()
        }
        if (order.paid == Order.PaymentStatus.TRYING &&
            order.messageSent == Order.MessageSent.NONE_SENT
        ) {
            val requestId = messagingService.receiveRequest(1)
            order.messageSent = Order.MessageSent.PAYMENT_TRYING
            logger.info { "Order ${order.id}: Payment Error message sent successfully,$REQUEST_ID $requestId" }
        }
    }

    private fun employeeHandleIssue(order: Order) {
        if (System.currentTimeMillis() - order.createdTime >= this.employeeTime) {
            logger.trace { "Order ${order.id}: Employee handle time for order over, returning.." }
            return
        }
        val list = employeeDb.exceptionsList
        val t =
            Thread {
                val op =
                    Retry.Operation { l ->
                        if (l.isNotEmpty()) {
                            logger.warn { "Order ${order.id}: Error in connecting to employee handle, trying again.." }
                            throw l.removeAt(0)
                        }
                        if (!order.addedToEmployeeHandle) {
                            employeeDb.receiveRequest(order)
                            order.addedToEmployeeHandle = true
                            logger.info { "Order ${order.id}: Added order to employee database" }
                        }
                    }
                val handleError =
                    Retry.HandleErrorIssue<Order> { o, _ ->
                        if (o != null &&
                            !o.addedToEmployeeHandle &&
                            System.currentTimeMillis() - order.createdTime < employeeTime
                        ) {
                            val qt = QueueTask(order, QueueTask.TaskType.EMPLOYEE_DB, -1)
                            updateQueue(qt)
                            logger.warn { "Order ${order.id}: Error in adding to employee db, trying to queue task.." }
                        }
                    }
                val r =
                    Retry(
                        op,
                        handleError,
                        numOfRetries,
                        retryDuration,
                        { e -> DatabaseUnavailableException::class.java.isAssignableFrom(e.javaClass) },
                    )
                try {
                    r.perform(list, order)
                } catch (e1: Exception) {
                    logger.error(e1) { DEFAULT_EXCEPTION_MESSAGE }
                }
            }
        t.start()
    }

    private fun doTasksInQueue() {
        if (queueItems != 0) {
            val qt = queue?.peek() ?: return
            logger.trace { "Order ${qt.order.id}: Started doing task of type ${qt.getType()}" }
            if (qt.isFirstAttempt()) {
                qt.firstAttemptTime = System.currentTimeMillis()
            }
            if (System.currentTimeMillis() - qt.firstAttemptTime >= queueTaskTime) {
                tryDequeue()
                logger.trace { "Order ${qt.order.id}: This queue task of type ${qt.getType()} does not need to be done anymore (timeout), dequeue.." }
            } else {
                when (qt.taskType) {
                    QueueTask.TaskType.PAYMENT -> doPaymentTask(qt)
                    QueueTask.TaskType.MESSAGING -> doMessagingTask(qt)
                    QueueTask.TaskType.EMPLOYEE_DB -> doEmployeeDbTask(qt)
                }
            }
        }
        if (queueItems == 0) {
            logger.trace { "Queue is empty, returning.." }
        } else {
            Thread.sleep(queueTaskTime / 3)
            tryDoingTasksInQueue()
        }
    }

    private fun doEmployeeDbTask(qt: QueueTask) {
        if (qt.order.addedToEmployeeHandle) {
            tryDequeue()
            logger.trace { "Order ${qt.order.id}: This employee handle task already done, dequeue.." }
        } else {
            employeeHandleIssue(qt.order)
            logger.debug { "Order ${qt.order.id}: Trying to connect to employee handle.." }
        }
    }

    private fun doMessagingTask(qt: QueueTask) {
        if (qt.order.messageSent == Order.MessageSent.PAYMENT_FAIL ||
            qt.order.messageSent == Order.MessageSent.PAYMENT_SUCCESSFUL
        ) {
            tryDequeue()
            logger.trace { "Order ${qt.order.id}: This messaging task already done, dequeue.." }
        } else if (qt.messageType == 1 &&
            (
                qt.order.messageSent != Order.MessageSent.NONE_SENT ||
                    qt.order.paid != Order.PaymentStatus.TRYING
            )
        ) {
            tryDequeue()
            logger.trace { "Order ${qt.order.id}: This messaging task does not need to be done, dequeue.." }
        } else {
            when (qt.messageType) {
                0 -> {
                    sendPaymentFailureMessage(qt.order)
                    logger.debug { "Order ${qt.order.id}$TRY_CONNECTING_MSG_SVC" }
                }
                1 -> {
                    sendPaymentPossibleErrorMsg(qt.order)
                    logger.debug { "Order ${qt.order.id}$TRY_CONNECTING_MSG_SVC" }
                }
                2 -> {
                    sendSuccessMessage(qt.order)
                    logger.debug { "Order ${qt.order.id}$TRY_CONNECTING_MSG_SVC" }
                }
            }
        }
    }

    private fun doPaymentTask(qt: QueueTask) {
        if (qt.order.paid != Order.PaymentStatus.TRYING) {
            tryDequeue()
            logger.trace { "Order ${qt.order.id}: This payment task already done, dequeueing.." }
        } else {
            sendPaymentRequest(qt.order)
            logger.debug { "Order ${qt.order.id}: Trying to connect to payment service.." }
        }
    }
}