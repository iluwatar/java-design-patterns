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
  public static void main(String[] args){

  }
}
