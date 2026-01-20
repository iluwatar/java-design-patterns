package com.mukul.messaging;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NotificationService is a message consumer that processes notification-related messages.
 * It listens to order events and sends notifications to customers.
 */
public class NotificationService {
  private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

  /**
   * Handles incoming messages related to orders.
   *
   * @param message the message to process
   */
  public void handleMessage(Message message) {
    LOGGER.info("Notification Service received message [{}]: {}",
        message.getId(), message.getContent());

    if (message.getContent().contains("Order Created")) {
      sendOrderConfirmation(message);
    } else if (message.getContent().contains("Order Updated")) {
      sendOrderUpdate(message);
    } else if (message.getContent().contains("Order Cancelled")) {
      sendCancellationNotice(message);
    }
  }

  private void sendOrderConfirmation(Message message) {
    LOGGER.info("Sending order confirmation for message: {}", message.getId());
    // Simulate sending email/SMS
    try {
      Thread.sleep(50);
      LOGGER.info("Order confirmation sent successfully");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.error("Notification send interrupted", e);
    }
  }

  private void sendOrderUpdate(Message message) {
    LOGGER.info("Sending order update notification for message: {}", message.getId());
    try {
      Thread.sleep(50);
      LOGGER.info("Order update notification sent successfully");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.error("Notification send interrupted", e);
    }
  }

  private void sendCancellationNotice(Message message) {
    LOGGER.info("Sending cancellation notice for message: {}", message.getId());
    try {
      Thread.sleep(50);
      LOGGER.info("Cancellation notice sent successfully");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.error("Notification send interrupted", e);
    }
  }
}
