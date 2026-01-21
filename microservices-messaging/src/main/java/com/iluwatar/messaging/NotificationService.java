package com.iluwatar.messaging;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * NotificationService is a message consumer that processes notification-related messages from Kafka.
 * It listens to order events and sends notifications to customers.
 *
 * <p>This service runs in its own Kafka consumer group (notification-group) which allows it to:
 * <ul>
 *   <li>Process messages independently from other services</li>
 *   <li>Scale horizontally by adding more instances to the consumer group</li>
 *   <li>Resume from last committed offset if the service restarts</li>
 * </ul>
 */
public class NotificationService {
  private static final Logger LOGGER = LoggerFactory.getLogger(NotificationService.class);

  /**
   * Handles incoming messages related to orders from Kafka.
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
    } else {
      LOGGER.debug("No notification action needed for: {}", message.getContent());
    }
  }

  private void sendOrderConfirmation(Message message) {
    LOGGER.info("Sending order confirmation for message: {}", message.getId());
    // Simulate sending email/SMS notification to customer
    try {
      Thread.sleep(50);
      LOGGER.info("Order confirmation sent successfully for: {}", message.getContent());
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.error("Notification send interrupted", e);
    }
  }

  private void sendOrderUpdate(Message message) {
    LOGGER.info("Sending order update notification for message: {}", message.getId());
    // Simulate sending update notification
    try {
      Thread.sleep(50);
      LOGGER.info("Order update notification sent successfully for: {}", message.getContent());
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.error("Notification send interrupted", e);
    }
  }

  private void sendCancellationNotice(Message message) {
    LOGGER.info("Sending cancellation notice for message: {}", message.getId());
    // Simulate sending cancellation notification
    try {
      Thread.sleep(50);
      LOGGER.info("Cancellation notice sent successfully for: {}", message.getContent());
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.error("Notification send interrupted", e);
    }
  }
}