package com.mukul.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * InventoryService is a message consumer that processes inventory-related messages.
 * It listens to order events and updates inventory accordingly.
 */
public class InventoryService {
  private static final Logger LOGGER = LoggerFactory.getLogger(InventoryService.class);

  /**
   * Handles incoming messages related to orders.
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
    }
  }

  private void updateInventory(Message message) {
    LOGGER.info("Updating inventory for message: {}", message.getId());
    // Simulate inventory update
    try {
      Thread.sleep(100);
      LOGGER.info("Inventory updated successfully");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.error("Inventory update interrupted", e);
    }
  }

  private void restoreInventory(Message message) {
    LOGGER.info("Restoring inventory for message: {}", message.getId());
    // Simulate inventory restoration
    try {
      Thread.sleep(100);
      LOGGER.info("Inventory restored successfully");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      LOGGER.error("Inventory restore interrupted", e);
    }
  }
}
