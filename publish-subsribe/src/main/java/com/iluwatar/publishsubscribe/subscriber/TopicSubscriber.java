package com.iluwatar.publishsubscribe.subscriber;

import com.iluwatar.publishsubscribe.jms.JmsUtil;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

/**
 * JMS topic subscriber that supports different subscription types.
 * Handles durable, non-durable, and shared subscriptions.
 */
public class TopicSubscriber implements MessageListener {
  private final Session session;
  private final MessageConsumer consumer;
  private final String name;
  private final SubscriberType type;
  private final String subscriptionName;

  /**
   * Creates a new topic subscriber with the specified configuration.
   *
   * @param name      The name of the subscriber
   * @param topicName The topic to subscribe to
   * @param type      The type of subscription
   * @param clientId  Client ID for durable subscriptions
   * @throws JMSException if there's an error creating the subscriber
   */
  public TopicSubscriber(String name, String topicName, SubscriberType type, String clientId)
      throws JMSException {
    this.name = name;
    this.type = type;
    this.subscriptionName = generateSubscriptionName(name, type);
    session = JmsUtil.createSession(clientId);
    Topic topic = session.createTopic(topicName);

    switch (type) {
      case DURABLE:
        // Durable subscribers maintain subscription state even when offline
        consumer = session.createDurableSubscriber(topic, subscriptionName);
        break;
      case SHARED:
        // Shared subscribers distribute the message load using message groups
        consumer = session.createConsumer(topic,
            "JMSXGroupID = '" + subscriptionName + "'");
        break;
      case SHARED_DURABLE:
        consumer = session.createDurableSubscriber(topic, subscriptionName);
        break;
      default:
        consumer = session.createConsumer(topic);
    }

    consumer.setMessageListener(this);
    System.out.println("Created " + type + " subscriber: " + name);
  }

  private String generateSubscriptionName(String name, SubscriberType type) {
    switch (type) {
      case DURABLE:
        return "durable-" + name;
      case SHARED_DURABLE:
        return "shared-durable-" + name;
      case SHARED:
        return "shared-" + name;
      default:
        return name;
    }
  }

  @Override
  public void onMessage(Message message) {
    try {
      if (message instanceof TextMessage) {
        TextMessage textMessage = (TextMessage) message;
        System.out.println(name + " (" + type + ") received: " + textMessage.getText());
      }
    } catch (JMSException e) {
      e.printStackTrace();
    }
  }

  /**
   * Cleanup subscriber resources, ensuring proper unsubscribe for durable
   * subscribers.
   */
  public void close() throws JMSException {
    if (type == SubscriberType.DURABLE || type == SubscriberType.SHARED_DURABLE) {
      try {
        consumer.close();
        session.unsubscribe(subscriptionName);
      } catch (JMSException e) {
        System.err.println("Error unsubscribing " + name + ": " + e.getMessage());
      }
    }
    if (session != null) {
      session.close();
    }
  }

  public SubscriberType getType() {
    return type;
  }
}
