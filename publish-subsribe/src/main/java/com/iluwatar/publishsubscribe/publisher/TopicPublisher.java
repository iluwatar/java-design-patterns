package com.iluwatar.publishsubscribe.publisher;

import com.iluwatar.publishsubscribe.jms.JmsUtil;
import com.iluwatar.publishsubscribe.model.Message;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 * JMS topic publisher that supports persistent messages and message grouping
 * for shared subscriptions.
 */
public class TopicPublisher {
  private final Session session;
  private final MessageProducer producer;

  /**
   * Creates a new publisher for the specified topic.
   */
  public TopicPublisher(String topicName) throws JMSException {
    session = JmsUtil.createSession();
    Topic topic = session.createTopic(topicName);
    producer = session.createProducer(topic);
    producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
  }

  /**
   * Publishes a message to the topic.
   */
  public void publish(Message message) throws JMSException {
    TextMessage textMessage = session.createTextMessage(message.getContent());
    textMessage.setStringProperty("JMSXGroupID", "group-" + message.getTopic());
    producer.send(textMessage);
    System.out.println("Published to topic " + message.getTopic() + ": " + message.getContent());
  }

  /**
   * Closes the publisher resources.
   */
  public void close() throws JMSException {
    if (session != null) {
      session.close();
    }
  }
}
