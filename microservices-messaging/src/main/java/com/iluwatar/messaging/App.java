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
package com.iluwatar.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * The Microservices Messaging pattern enables asynchronous communication between services through
 * Apache Kafka. This example demonstrates how services can communicate without tight coupling.
 *
 * <p>In this example:
 * <ul>
 *   <li>OrderService acts as a message producer, publishing order events to Kafka</li>
 *   <li>InventoryService, PaymentService, and NotificationService act as consumers</li>
 *   <li>Apache Kafka acts as the message broker, routing messages between services</li>
 * </ul>
 *
 * <p>Key benefits demonstrated:
 * <ul>
 *   <li>Loose coupling - services don't directly depend on each other</li>
 *   <li>Asynchronous processing - producers don't wait for consumers</li>
 *   <li>Scalability - multiple consumers can process messages independently</li>
 *   <li>Resilience - if one consumer fails, others continue processing</li>
 *   <li>Message persistence - Kafka stores messages for reliability</li>
 * </ul>
 *
 * <p><b>Prerequisites:</b> This example requires a running Kafka instance.
 * Start Kafka locally:
 * <pre>
 * # Start Zookeeper
 * bin/zookeeper-server-start.sh config/zookeeper.properties
 *
 * # Start Kafka
 * bin/kafka-server-start.sh config/server.properties
 *
 * # Create topic
 * bin/kafka-topics.sh --create --topic order-topic --bootstrap-server localhost:9092
 * </pre>
 */
public class App {
  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
  private static final String BOOTSTRAP_SERVERS = "localhost:9092";

  /**
   * Program entry point.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) throws InterruptedException {
    LOGGER.info("Starting Microservices Messaging Pattern with Apache Kafka");

    // Create Kafka producer
    KafkaMessageProducer producer = new KafkaMessageProducer(BOOTSTRAP_SERVERS);

    // Create consumer services
    InventoryService inventoryService = new InventoryService();
    PaymentService paymentService = new PaymentService();
    NotificationService notificationService = new NotificationService();

    // Create Kafka consumers
    KafkaMessageConsumer inventoryConsumer = new KafkaMessageConsumer(
        BOOTSTRAP_SERVERS, "inventory-group", "order-topic", inventoryService::handleMessage);

    KafkaMessageConsumer paymentConsumer = new KafkaMessageConsumer(
        BOOTSTRAP_SERVERS, "payment-group", "order-topic", paymentService::handleMessage);

    KafkaMessageConsumer notificationConsumer = new KafkaMessageConsumer(
        BOOTSTRAP_SERVERS, "notification-group", "order-topic",
        notificationService::handleMessage);

    // Start consumers in separate threads
    ExecutorService executor = Executors.newFixedThreadPool(3);
    executor.submit(inventoryConsumer);
    executor.submit(paymentConsumer);
    executor.submit(notificationConsumer);

    // Give consumers time to subscribe
    Thread.sleep(2000);

    // Create producer service
    OrderService orderService = new OrderService(producer);

    // Demonstrate the messaging pattern
    LOGGER.info("\n=== Creating Order ===");
    orderService.createOrder("ORDER-001");

    Thread.sleep(2000);

    LOGGER.info("\n=== Updating Order ===");
    orderService.updateOrder("ORDER-001");

    Thread.sleep(2000);

    LOGGER.info("\n=== Cancelling Order ===");
    orderService.cancelOrder("ORDER-001");

    Thread.sleep(2000);

    // Cleanup
    LOGGER.info("\nShutting down...");
    inventoryConsumer.stop();
    paymentConsumer.stop();
    notificationConsumer.stop();
    producer.close();

    executor.shutdown();
    executor.awaitTermination(5, TimeUnit.SECONDS);

    LOGGER.info("Microservices Messaging Pattern demonstration completed");
  }
}