package com.iluwatar.publishsubscribe;

import com.iluwatar.publishsubscribe.jms.JmsUtil;
import com.iluwatar.publishsubscribe.model.Message;
import com.iluwatar.publishsubscribe.publisher.TopicPublisher;
import com.iluwatar.publishsubscribe.subscriber.TopicSubscriber;
import com.iluwatar.publishsubscribe.subscriber.SubscriberType;

import java.util.ArrayList;
import java.util.List;

/**
 * Main application demonstrating different aspects of JMS publish-subscribe
 * pattern.
 */
public final class App {
    private static TopicPublisher publisher;

    public static void main(String[] args) {
        try {
            publisher = new TopicPublisher("NEWS");

            // Run each demonstration independently
            demonstrateBasicPubSub();
            demonstrateDurableSubscriptions();
            demonstrateSharedSubscriptions();

        } catch (Exception e) {
            System.err.println("Error in publish-subscribe demo: " + e.getMessage());
            e.printStackTrace();
        } finally {
            cleanup(null);
        }
        JmsUtil.closeConnection();
    }

    /**
     * Demonstrates basic publish-subscribe with non-durable subscribers.
     * All subscribers receive all messages.
     */
    private static void demonstrateBasicPubSub() throws Exception {
        System.out.println("\n=== Basic Publish-Subscribe Demonstration ===");
        List<TopicSubscriber> subscribers = new ArrayList<>();

        try {
            // Create basic subscribers
            subscribers.add(new TopicSubscriber("BasicSub1", "NEWS", SubscriberType.NONDURABLE, null));
            subscribers.add(new TopicSubscriber("BasicSub2", "NEWS", SubscriberType.NONDURABLE, null));
            Thread.sleep(100); // Wait for subscribers to initialize

            // Publish messages - all subscribers should receive all messages
            publisher.publish(new Message("Basic message 1", "NEWS"));
            publisher.publish(new Message("Basic message 2", "NEWS"));

            Thread.sleep(1000); // Wait for message processing
        } finally {
            cleanup(subscribers);
            System.out.println("=== Basic Demonstration Completed ===\n");
        }
    }

    /**
     * Demonstrates durable subscriptions that persist messages when subscribers are
     * offline.
     */
    private static void demonstrateDurableSubscriptions() throws Exception {
        System.out.println("\n=== Durable Subscriptions Demonstration ===");
        List<TopicSubscriber> subscribers = new ArrayList<>();

        try {
            // Create durable subscriber
            TopicSubscriber durableSub = new TopicSubscriber("DurableSub", "NEWS",
                    SubscriberType.DURABLE, "durable-client");
            subscribers.add(durableSub);
            Thread.sleep(100);

            // First message - subscriber is online
            publisher.publish(new Message("Durable message while online", "NEWS"));
            Thread.sleep(500);

            // Disconnect subscriber
            durableSub.close();
            subscribers.clear();

            // Send message while subscriber is offline
            publisher.publish(new Message("Durable message while offline", "NEWS"));
            Thread.sleep(500);

            // Reconnect subscriber - should receive offline message
            subscribers.add(new TopicSubscriber("DurableSub", "NEWS",
                    SubscriberType.DURABLE, "durable-client"));
            Thread.sleep(1000);

        } finally {
            cleanup(subscribers);
            System.out.println("=== Durable Demonstration Completed ===\n");
        }
    }

    /**
     * Demonstrates shared subscriptions where messages are distributed among
     * subscribers.
     */
    private static void demonstrateSharedSubscriptions() throws Exception {
        System.out.println("\n=== Shared Subscriptions Demonstration ===");
        List<TopicSubscriber> subscribers = new ArrayList<>();

        try {
            // Create shared subscribers
            subscribers.add(new TopicSubscriber("SharedSub1", "NEWS", SubscriberType.SHARED, null));
            subscribers.add(new TopicSubscriber("SharedSub2", "NEWS", SubscriberType.SHARED, null));
            Thread.sleep(100);

            // Messages should be distributed between subscribers
            for (int i = 1; i <= 4; i++) {
                publisher.publish(new Message("Shared message " + i, "NEWS"));
                Thread.sleep(100);
            }

            Thread.sleep(1000);
        } finally {
            cleanup(subscribers);
            System.out.println("=== Shared Demonstration Completed ===\n");
        }
    }

    /**
     * Cleanup specified subscribers and optionally the publisher.
     */
    private static void cleanup(List<TopicSubscriber> subscribers) {
        try {
            if (subscribers != null) {
                for (TopicSubscriber subscriber : subscribers) {
                    if (subscriber != null) {
                        subscriber.close();
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error during subscriber cleanup: " + e.getMessage());
        }
    }
}
