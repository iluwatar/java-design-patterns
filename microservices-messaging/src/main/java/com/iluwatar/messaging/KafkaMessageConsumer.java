package com.iluwatar.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

/**
 * Kafka message consumer that subscribes to topics and processes messages.
 */
public class KafkaMessageConsumer implements AutoCloseable, Runnable {
  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaMessageConsumer.class);
  private final KafkaConsumer<String, String> consumer;
  private final ObjectMapper objectMapper;
  private final String topic;
  private final Consumer<Message> messageHandler;
  private final AtomicBoolean running = new AtomicBoolean(true);

  /**
   * Creates a new Kafka message consumer.
   *
   * @param bootstrapServers Kafka bootstrap servers
   * @param groupId          consumer group ID
   * @param topic            topic to subscribe to
   * @param messageHandler   handler for received messages
   */
  public KafkaMessageConsumer(String bootstrapServers, String groupId, String topic,
                              Consumer<Message> messageHandler) {
    Properties props = new Properties();
    props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");

    this.consumer = new KafkaConsumer<>(props);
    this.objectMapper = new ObjectMapper();
    this.objectMapper.registerModule(new JavaTimeModule());
    this.topic = topic;
    this.messageHandler = messageHandler;
  }

  @Override
  public void run() {
    try {
      consumer.subscribe(Collections.singletonList(topic));
      LOGGER.info("Consumer subscribed to topic: {}", topic);

      while (running.get()) {
        ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
        records.forEach(record -> {
          try {
            Message message = objectMapper.readValue(record.value(), Message.class);
            LOGGER.info("Received message from topic '{}': {}", topic, message.getId());
            messageHandler.accept(message);
          } catch (Exception e) {
            LOGGER.error("Error processing message: {}", e.getMessage(), e);
          }
        });
      }
    } catch (Exception e) {
      LOGGER.error("Consumer error: {}", e.getMessage(), e);
    } finally {
      consumer.close();
      LOGGER.info("Consumer closed for topic: {}", topic);
    }
  }

  /**
   * Stops the consumer.
   */
  public void stop() {
    running.set(false);
  }

  @Override
  public void close() {
    stop();
  }
}
