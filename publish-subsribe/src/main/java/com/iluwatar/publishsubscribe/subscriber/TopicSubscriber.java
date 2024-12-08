package com.iluwatar.publishsubscribe.subscriber;

import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.JMSException;
import com.iluwatar.publishsubscribe.jms.JmsUtil;

/**
 * JMS topic subscriber that supports different subscription types:
 * - Non-durable: Regular subscriber that misses messages when offline
 * - Durable: Receives missed messages after reconnecting
 * - Shared: Multiple subscribers share the message load
 */
public class TopicSubscriber implements MessageListener {
    private final Session session;
    private final MessageConsumer consumer;
    private final String name;
    private final SubscriberType type;
    private final String subscriptionName;

    public TopicSubscriber(String name, String topicName, SubscriberType type, String clientId) throws JMSException {
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
     * Cleanup subscriber resources, ensuring proper unsubscribe for durable subscribers.
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
