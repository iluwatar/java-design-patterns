package com.mukul.messaging;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PaymentService is a message consumer that processes payment-related messages.
 * It listens to order events and handles payment processing.
 */
public class PaymentService {
  private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

  /**
   * Handles incoming messages related to orders.
   *
   * @param message the message to process
   */
  public void handleMessage(Message message) {
    LOGGER.info("Payment Service received message [{}]: {}",
        message.getId(), message.getContent());

    if (message.getContent().contains("Order Created")) {
      processPayment(message);
    } else if (message.getContent().contains("Order Cancelled")) {
      refundPayment(message);
    }
  }

  private void processPayment(Message message) {
    LOGGER.info("Processing payment for message: {}", message.getId());
    // Simulate payment processing
    try {
      Thread.sleep(150);
      LOGGER.info("Payment processed successfully");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.error("Payment processing interrupted", e);
    }
  }

  private void refundPayment(Message message) {
    LOGGER.info("Refunding payment for message: {}", message.getId());
    // Simulate payment refund
    try {
      Thread.sleep(150);
      LOGGER.info("Payment refunded successfully");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.error("Payment refund interrupted", e);
    }
  }
}
