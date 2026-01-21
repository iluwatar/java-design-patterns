package com.iluwatar.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Properties;

/**
 * Kafka message producer that publishes messages to Kafka topics.
 */
public class KafkaMessageProducer implements AutoCloseable {
  private static final Logger LOGGER = LoggerFactory.getLogger(KafkaMessageProducer.class);
  private final KafkaProducer<String, String> producer;
  private final ObjectMapper objectMapper;

  /**
   * Creates a new Kafka message producer.
   *
   * @param bootstrapServers Kafka bootstrap servers
   */
  public KafkaMessageProducer(String bootstrapServers) {
    Properties props = new Properties();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    props.put(ProducerConfig.ACKS_CONFIG, "all");
    props.put(ProducerConfig.RETRIES_CONFIG, 3);

    this.producer = new KafkaProducer<>(props);
    this.objectMapper = new ObjectMapper();
    this.objectMapper.registerModule(new JavaTimeModule());
  }

  /**
   * Publishes a message to a Kafka topic.
   *
   * @param topic   the topic to publish to
   * @param message the message to publish
   */
  public void publish(String topic, Message message) {
    try {
      String json = objectMapper.writeValueAsString(message);
      ProducerRecord<String, String> record = new ProducerRecord<>(topic, message.getId(), json);

      producer.send(record, (metadata, exception) -> {
        if (exception != null) {
          LOGGER.error("Failed to publish message to topic {}: {}", topic, exception.getMessage());
        } else {
          LOGGER.info("Published message to topic '{}' [partition={}, offset={}]",
              topic, metadata.partition(), metadata.offset());
        }
      });

    } catch (Exception e) {
      LOGGER.error("Error serializing message: {}", e.getMessage(), e);
    }
  }

  @Override
  public void close() {
    if (producer != null) {
      producer.flush();
      producer.close();
      LOGGER.info("Kafka producer closed");
    }
  }
}
