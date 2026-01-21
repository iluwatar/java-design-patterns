package com.iluwatar.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * InventoryService is a message consumer that processes inventory-related messages from Kafka.
 * It listens to order events and updates inventory accordingly.
 *
 * <p>This service runs in its own Kafka consumer group (inventory-group) which allows it to:
 * <ul>
 *   <li>Process messages independently from other services</li>
 *   <li>Scale horizontally by adding more instances to the consumer group</li>
 *   <li>Resume from last committed offset if the service restarts</li>
 * </ul>
 */
public class InventoryService {
  private static final Logger LOGGER = LoggerFactory.getLogger(InventoryService.class);

  /**
   * Handles incoming messages related to orders from Kafka.
   *
   * @param message the message to process
   */
  public void handleMessage(Message message) {
    LOGGER.info("Inventory Service received message [{}]: {}",
        message.getId(), message.getContent());

    if (message.getContent().contains("Order Created")) {
      updateInventory(message);
    } else if (message.getContent().contains("Order Cancelled")) {
      restoreInventory(message);
    } else {
      LOGGER.debug("No inventory action needed for: {}", message.getContent());
    }
  }

  private void updateInventory(Message message) {
    LOGGER.info("Updating inventory for message: {}", message.getId());
    // Simulate inventory update - reserve stock for the order
    try {
      Thread.sleep(100);
      LOGGER.info("Inventory updated successfully for: {}", message.getContent());
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.error("Inventory update interrupted", e);
    }
  }

  private void restoreInventory(Message message) {
    LOGGER.info("Restoring inventory for message: {}", message.getId());
    // Simulate inventory restoration - release reserved stock
    try {
      Thread.sleep(100);
      LOGGER.info("Inventory restored successfully for: {}", message.getContent());
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.error("Inventory restore interrupted", e);
    }
  }
}