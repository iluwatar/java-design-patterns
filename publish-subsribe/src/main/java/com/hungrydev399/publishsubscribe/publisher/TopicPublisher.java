package com.hungrydev399.publishsubscribe.publisher;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import com.hungrydev399.publishsubscribe.jms.JmsUtil;
import com.hungrydev399.publishsubscribe.model.Message;

/**
 * JMS topic publisher that supports persistent messages and message grouping
 * for shared subscriptions.
 */
public class TopicPublisher {
    private final Session session;
    private final MessageProducer producer;

    public TopicPublisher(String topicName) throws JMSException {
        session = JmsUtil.createSession();
        Topic topic = session.createTopic(topicName);
        producer = session.createProducer(topic);
        // Enable persistent delivery for durable subscriptions
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
    }

    public void publish(Message message) throws JMSException {
        TextMessage textMessage = session.createTextMessage(message.getContent());
        // Group messages to enable load balancing for shared subscribers
        textMessage.setStringProperty("JMSXGroupID", "group-" + message.getTopic());
        producer.send(textMessage);
        System.out.println("Published to topic " + message.getTopic() + ": " + message.getContent());
    }

    public void close() throws JMSException {
        if (session != null) {
            session.close();
        }
    }
}
