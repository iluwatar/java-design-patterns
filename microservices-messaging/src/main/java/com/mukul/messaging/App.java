package com.mukul.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Microservices Messaging pattern enables asynchronous communication between services through
 * a message broker. This example demonstrates how services can communicate without tight coupling.
 *
 * <p>In this example:
 * <ul>
 *   <li>OrderService acts as a message producer, publishing order events</li>
 *   <li>InventoryService, PaymentService, and NotificationService act as consumers</li>
 *   <li>MessageBroker routes messages from producers to all subscribed consumers</li>
 * </ul>
 *
 * <p>Key benefits demonstrated:
 * <ul>
 *   <li>Loose coupling - services don't directly depend on each other</li>
 *   <li>Asynchronous processing - producers don't wait for consumers</li>
 *   <li>Scalability - multiple consumers can process messages independently</li>
 *   <li>Resilience - if one consumer fails, others continue processing</li>
 * </ul>
 */
public class App {
  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * Program entry point.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) throws InterruptedException {
    LOGGER.info("Starting Microservices Messaging Pattern demonstration");

    // Create the message broker
    final MessageBroker broker = new MessageBroker();

    // Create consumer services
    final InventoryService inventoryService = new InventoryService();
    final PaymentService paymentService = new PaymentService();
    final NotificationService notificationService = new NotificationService();

    // Subscribe consumers to the order topic
    broker.subscribe("order-topic", inventoryService::handleMessage);
    broker.subscribe("order-topic", paymentService::handleMessage);
    broker.subscribe("order-topic", notificationService::handleMessage);

    // Create producer service
    final OrderService orderService = new OrderService(broker);

    // Demonstrate the messaging pattern
    LOGGER.info("=== Creating Order ===");
    orderService.createOrder("ORDER-001");

    // Allow time for asynchronous processing
    Thread.sleep(500);

    LOGGER.info("\n=== Updating Order ===");
    orderService.updateOrder("ORDER-001");

    Thread.sleep(500);

    LOGGER.info("\n=== Cancelling Order ===");
    orderService.cancelOrder("ORDER-001");

    Thread.sleep(500);

    LOGGER.info("\nMicroservices Messaging Pattern demonstration completed");
  }
}
