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
