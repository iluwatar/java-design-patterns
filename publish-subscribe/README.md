---
title: "Publisher-Subscriber Pattern in Java: Decoupling the solution with asynchronous communication"
shortTitle: Proxy
description: "Explore the Proxy design pattern in Java with detailed examples. Learn how it provides controlled access, facilitates lazy initialization, and ensures security. Ideal for developers looking to implement advanced Java techniques."
category: Structural
language: en
tag:
    - Decoupling
    - Encapsulation
    - Gang Of Four
    - Lazy initialization
    - Proxy
    - Security
    - Wrapping
---

## Intent of the Publisher-Subscriber Design Pattern

The publisher-subscriber design pattern is widely used in software architecture to transmit data between various components in a system.
It is a behavioral design pattern aimed at achieving loosely coupled communication between objects.
The primary intent is to allow a one-to-many dependency relationship where one object (the Publisher) notifies multiple other objects (the Subscribers) about changes or events,
without needing to know who or what the subscribers are.

## Detailed Explanation of Publisher-Subscriber Pattern with Real-World Examples

- Messaging systems like Kafka, RabbitMQ, AWS SNS, JMS
    - **Kafka** : publishes messages to topics and subscribers consumes them in real time for analytics, logs or other purposes.
    - **RabbitMQ** : Uses exchanges as publisher and queues as subscribers to route messages
    - **AWS SNS** : Simple Notification Service (SNS) received the messages from publishers with topic and the subscribers on that topic will receive the messages. (SQS, Lambda functions, emails, SMS)


- Event driven microservices
    - **Publisher** : Point of Sale(PoS) system records the sale of an item and publish the event
    - **Subscribers** : Inventory management service updates stock, Billing service sends e-bill to customer


- Newsletter subscriptions
    - **Publisher** : Writes a new blog post and publish to subscribers
    - **Subscribers** : All the subscribers to the newsletter receive the email

## Programmatic Example of Publisher-Subscriber Pattern in Java

First we need to identify the Event on which we need the pub-sub methods to trigger.
For example:

- Sending alerts based on the weather events such as earthquakes, floods and tornadoes
- Sending an email to different customer support accounts when a support ticket is created.

The Message class below will hold the content of the message we need to pass between the publisher and the subscribers.

```java
public record Message(Object content) {
}

```

The Topic class will have the topic **name** based on the event

- Weather events TopicName WEATHER
- Support ticket created TopicName CUSTOMER_SUPPORT
  Also the Topic contains a list of subscribers that will listen to that topic

We can add or remove subscribers from the subscription to the topic

```java
public class Topic {

    private final TopicName name;
    private final Set<Subscriber> subscribers = new CopyOnWriteArraySet<>();
    //...//
}
```

Then we can create the publisher. The publisher class has a set of topics.

- Each new topic has to be registered in the publisher.
- Publish method will publish the _Message_ to the corresponding _Topic_.

```java
public class PublisherImpl implements Publisher {

    private static final Logger logger = LoggerFactory.getLogger(PublisherImpl.class);
    private final Set<Topic> topics = new HashSet<>();

    @Override
    public void registerTopic(Topic topic) {
        topics.add(topic);
    }

    @Override
    public void publish(Topic topic, Message message) {
        if (!topics.contains(topic)) {
            logger.error("This topic is not registered: {}", topic.getName());
            return;
        }
        topic.publish(message);
    }
}
```

Finally, we can Subscribers to the Topics we want to listen to.

- For WEATHER topic we will create _WeatherSubscriber_
- For CUSTOMER_SUPPORT topic we will create _CustomerSupportSubscribe_

Both classes will have a _onMessage_ method which will take a Message input.

- On message method will verify the content of the message is as expected
- After content is verified it will perform the operation based on the message
    - _WeatherSubscriber_ will send a weather alert based on the _Message_
    - _CustomerSupportSubscribe_will send an email based on the _Message_

```java
public interface Subscriber {
    void onMessage(Message message);
}
```

And here is the invocation of the publisher and subscribers.

```java
public static void main(String[] args) {

    final String weatherSub1Name = "weatherSub1";
    final String weatherSub2Name = "weatherSub2";
    final String supportSub1Name = "supportSub1";
    final String supportSub2Name = "supportSub2";

    Topic weatherTopic = new Topic(TopicName.WEATHER);
    Topic supportTopic = new Topic(TopicName.CUSTOMER_SUPPORT);

    Publisher publisher = new PublisherImpl();
    publisher.registerTopic(weatherTopic);
    publisher.registerTopic(supportTopic);

    Subscriber weatherSub1 = new WeatherSubscriber(weatherSub1Name);
    Subscriber weatherSub2 = new WeatherSubscriber(weatherSub2Name);
    weatherTopic.addSubscriber(weatherSub1);
    weatherTopic.addSubscriber(weatherSub2);

    Subscriber supportSub1 = new CustomerSupportSubscriber(supportSub1Name);
    Subscriber supportSub2 = new CustomerSupportSubscriber(supportSub2Name);
    supportTopic.addSubscriber(supportSub1);
    supportTopic.addSubscriber(supportSub2);

    publisher.publish(weatherTopic, new Message(WeatherContent.earthquake));
    publisher.publish(supportTopic, new Message(CustomerSupportContent.DE));
}
```

Program output:

```
11:46:44.310 [main] INFO com.iluwatar.publish.subscribe.subscriber.WeatherSubscriber - Subscriber: weatherSub1 issued message: earthquake tsunami warning
11:46:44.311 [main] INFO com.iluwatar.publish.subscribe.subscriber.WeatherSubscriber - Subscriber: weatherSub2 issued message: earthquake tsunami warning
11:46:44.311 [main] INFO com.iluwatar.publish.subscribe.subscriber.CustomerSupportSubscriber - Subscriber: supportSub1 sent the email to: customer.support@test.de
11:46:44.311 [main] INFO com.iluwatar.publish.subscribe.subscriber.CustomerSupportSubscriber - Subscriber: supportSub2 sent the email to: customer.support@test.de
```

## When to Use the Publisher-Subscriber Pattern

- Event-Driven Systems
    - Use Pub/Sub when your system relies on events (e.g., user registration, payment completion).
    - Example: After a user registers, send a welcome email and log the action simultaneously.

- Asynchronous Communication
    - When tasks can be performed without waiting for immediate responses.
    - Example: In an e-commerce app, notify the warehouse and the user after a successful order.

- Decoupling Components
    - Ideal for systems where producers and consumers should not depend on each other.
    - Example: A logging service listens for logs from multiple microservices.

- Scaling Systems
    - Useful when you need to scale services without changing the core application logic.
    - Example: Broadcasting messages to thousands of clients (chat applications, IoT).

- Broadcasting Notifications
    - When a message should be delivered to multiple receivers.
    - Example: Sending promotional offers to multiple user devices.

- Microservices Communication
    - Allow independent services to communicate without direct coupling.
    - Example: An order service publishes an event, and both the billing and shipping services process it.

## When to avoid the Publisher-Subscriber Pattern

- Simple applications where direct calls suffice.
- Strong consistency requirements (e.g., banking transactions).
- Low-latency synchronous communication needed.

## Benefits and Trade-offs of Publisher-Subscriber Pattern

### Benefits:

- Decoupling
    - Publishers and subscribers are independent of each other.
    - Publishers don’t need to know who the subscribers are, and vice versa.
    - Changes in one component don’t affect the other.
- Scalability
    - New subscribers can be added without modifying publishers.
    - Supports distributed systems where multiple services consume the same events.
- Dynamic Subscription
    - Subscribers can subscribe/unsubscribe at runtime.
    - Enables flexible event-driven architectures.
- Asynchronous Communication
    - Publishers and subscribers operate independently, improving performance.
    - Useful for background processing (e.g., notifications, logging).
- Broadcast Communication
    - A single event can be consumed by multiple subscribers.
    - Useful for fan-out scenarios (e.g., notifications, analytics).
- Resilience & Fault Tolerance
    - If a subscriber fails, others can still process messages.
    - Message brokers (e.g., Kafka, RabbitMQ) can retry or persist undelivered messages.

### Trade-offs:

- Complexity in Debugging
    - Since publishers and subscribers are decoupled, tracing event flow can be difficult.
    - Requires proper logging and monitoring tools.
- Message Ordering & Consistency
    - Ensuring message order across subscribers can be challenging (e.g., Kafka vs. RabbitMQ).
    - Some systems may process events out of order.
- Potential Latency
    - Asynchronous processing introduces delays compared to direct calls.
    - Not ideal for real-time synchronous requirements.

## Related Java Design Patterns

* [Observer Pattern](https://github.com/sanurah/java-design-patterns/blob/master/observer/): Both involve a producer (subject/publisher) notifying consumers (observers/subscribers). Observer is synchronous & tightly coupled (observers know the subject). Pub-Sub is asynchronous & decoupled (via a message broker).
* [Mediator Pattern](https://github.com/sanurah/java-design-patterns/blob/master/mediator/): A mediator centralizes communication between components (like a message broker in Pub-Sub). Mediator focuses on reducing direct dependencies between objects. Pub-Sub focuses on broadcasting events to unknown subscribers.

## References and Credits

* [Apache Kafka – Pub-Sub Model](https://kafka.apache.org/documentation/#design_pubsub)
* [Microsoft – Publish-Subscribe Pattern](https://learn.microsoft.com/en-us/azure/architecture/patterns/publisher-subscriber)
* [Martin Fowler – Event-Driven Architecture](https://martinfowler.com/articles/201701-event-driven.html)
