package com.iluwatar.messaging;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PaymentService is a message consumer that processes payment-related messages from Kafka.
 * It listens to order events and handles payment processing.
 *
 * <p>This service runs in its own Kafka consumer group (payment-group) which allows it to:
 * <ul>
 *   <li>Process messages independently from other services</li>
 *   <li>Scale horizontally by adding more instances to the consumer group</li>
 *   <li>Resume from last committed offset if the service restarts</li>
 * </ul>
 */
public class PaymentService {
  private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);

  /**
   * Handles incoming messages related to orders from Kafka.
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
    } else {
      LOGGER.debug("No payment action needed for: {}", message.getContent());
    }
  }

  private void processPayment(Message message) {
    LOGGER.info("Processing payment for message: {}", message.getId());
    // Simulate payment processing - charge the customer
    try {
      Thread.sleep(150);
      LOGGER.info("Payment processed successfully for: {}", message.getContent());
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.error("Payment processing interrupted", e);
    }
  }

  private void refundPayment(Message message) {
    LOGGER.info("Refunding payment for message: {}", message.getId());
    // Simulate payment refund - return money to customer
    try {
      Thread.sleep(150);
      LOGGER.info("Payment refunded successfully for: {}", message.getContent());
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.error("Payment refund interrupted", e);
    }
  }
}