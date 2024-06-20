---
title: Publish-Subscribe
category: Behavioral
language: en
tag:
    - Distributed Systems
    - Messaging 
    - JMS
---

## Also known as

Pub-Sub Pattern

## Intent

To provide an asynchronous manner of handling messages between a message producer and a message consumer, without coupling the producer and consumer. 

## Explanation

Real-world example

> In online multipler games, players need real-time updates on player actions and game events. When updates needs to be broadcasted to multiple players the players act as subscribers to receive these updates. Depending on player level, account restrictions and so forth, a given player receives updates that another player may not receive and vice versa.

In plain words

> Publishers send messages that pertain to a certain topic and all subscribers to that topic will receive the message.

Wikipedia says

> Publish–subscribe is a messaging pattern where publishers categorize messages into classes that are received by subscribers. This is contrasted to the typical messaging pattern model where publishers send messages directly to subscribers.
Similarly, subscribers express interest in one or more classes and only receive messages that are of interest, without knowledge of which publishers, if any, there are.
Publish–subscribe is a sibling of the message queue paradigm, and is typically one part of a larger message-oriented middleware system

**Programmatic Example**

Consider an application in which a mortgage lender publishes interest rate updates to borrowers. When received, borrowers can decide whether they should refinance based on the updated rate.

First, we have the Lender class which acts as a publisher. The user can enter interest rates in which they will be published to all subscribers.

output:

Press enter to quit application
Enter: Rate

e.g. 6.8

```java
public class TLender {

    private TopicConnection tConnection;
    private TopicSession tSession;
    private Topic topic;
    private TopicPublisher publisher;

    public TLender(String topicCFName, String topicName) {

        try {
            //create context and retrieve objects from directory
            Context context = new InitialContext();
            TopicConnectionFactory topicCF = (TopicConnectionFactory) context.lookup(topicCFName);
            tConnection = topicCF.createTopicConnection();

            //create connection
            tSession = tConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            //Retrieve request and response queues
            topic = (Topic) context.lookup(topicName);

            //Create publisher
            publisher = tSession.createPublisher(topic);

            tConnection.start();
        } catch(NamingException e) {
            e.printStackTrace();
            LOGGER.error("An error has occurred!", e);
            System.exit(1);
        } catch(JMSException e) {
            e.printStackTrace();
            LOGGER.error("An error has occurred!", e);
            System.exit(1);
        }
    }

    private void publishRate(double newRate) {

        try {
            //create JMS message
            BytesMessage message = tSession.createBytesMessage();
            message.writeDouble(newRate);

            //publish message
            publisher.publish(message);
        } catch(JMSException e) {
            e.printStackTrace();
            LOGGER.error("An error has occurred!", e);
            System.exit(1);
        }
    }

    private void exit() {
        try {
            tConnection.close();
        } catch(JMSException e) {
            e.printStackTrace();
            LOGGER.error("An error has occurred!", e);
            System.exit(1);
        }
        System.exit(0);
    }

    public static void main(String[] args) {

        String topicCFName = null;
        String topicName = null;

        if(args.length == 2) {
            topicCFName = args[0];
            topicName = args[1];
        } else {
            System.out.println("Invalid arguments. Should be: ");
            System.out.println("java TLender [factory] [topic]");
            System.exit(1);
        }

        try {
            // Create and start activeMQ broker. Broker decouples publishers and subscribers.
            //Additionally brokers manage threads and asynchronous sending and receiving of messages.
            BrokerService broker = new BrokerService();
            broker.addConnector("tcp://localhost:61616");
            broker.start();

        } catch(Exception e) {
            e.printStackTrace();
            LOGGER.error("An error has occurred!", e);
        }

        TLender lender = new TLender(topicCFName, topicName);

        System.out.println ("TLender Application Started");
        System.out.println ("Press enter to quit application");
        System.out.println ("Enter: Rate");
        System.out.println("\ne.g. 6.8");

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            //Continuously read input and send as message to subscribers
            while(true) {
                System.out.print("> ");
                String line = reader.readLine();
                //Exit if user pressed enter or line is blank
                if (line == null || line.trim().length() == 0) {
                    System.out.println("Exiting...");
                    lender.exit();
                }
                else { //publish the entered rate
                    double newRate = Double.parseDouble(line);
                    lender.publishRate(newRate);
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
            LOGGER.error("An error has occurred!", e);
        }
    }

    public TopicConnection gettConnection() {
        return tConnection;
    }

    public TopicSession gettSession() {
        return tSession;
    }

    public Topic getTopic() {
        return topic;
    }
}
```

The Borrower class acts as a subscriber to a given topic, in this case mortgage interest rates. Evertime a new rate is published the subscriber receives the message.

output:

Initial rate is 6.0
Waiting for new rates...
Press enter to quit application

```java
public class TBorrower implements MessageListener {

    private TopicConnection tConnection;
    private TopicSession tSession;
    private Topic topic;
    private double currentRate;

    public TBorrower(String topicCFName, String topicName, double initialRate) {

        currentRate = initialRate;

        try {
            //create context and retrieve objects from directory
            Context context = new InitialContext();
            TopicConnectionFactory topicCF = (TopicConnectionFactory) context.lookup(topicCFName);
            tConnection = topicCF.createTopicConnection();

            //create connection
            tSession = tConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);

            //Retrieve request and response queues
            topic = (Topic) context.lookup(topicName);

            //Create subscriber and message listener
            TopicSubscriber subscriber = tSession.createSubscriber(topic);
            //Adds event listener to subscriber and uses onMessage as a callback
            subscriber.setMessageListener(this);

            tConnection.start();
            System.out.println("Initial rate is " + currentRate + " \nWaiting for new rates...");
        } catch(NamingException e) {
            e.printStackTrace();
            LOGGER.error("An error has occurred!", e);
            System.exit(1);
        } catch(JMSException e) {
            e.printStackTrace();
            LOGGER.error("An error has occurred!", e);
            System.exit(1);
        }
    }

    //This method is called asynchronously by the activeMQ broker
    public void onMessage(Message message) {

        try {
            BytesMessage bMessage = (BytesMessage) message;
            double newRate = ((BytesMessage) message).readDouble();

            if (currentRate - newRate >= 1)
                System.out.println("New Rate is " + newRate + " - Consider refinancing");
            else
                System.out.println("New Rate is " + newRate + " - Consider keeping current rate");
        } catch(JMSException e) {
            e.printStackTrace();
            LOGGER.error("An error occurred!", e);
            System.exit(1);
        }
        System.out.println("Waiting for new rates...");
    }

    private void exit() {
        try {
            tConnection.close();
        } catch(JMSException e) {
            e.printStackTrace();
            LOGGER.error("An error has occurred!", e);
            System.exit(1);
        }
        System.exit(0);
    }

    public static void main(String[] args) {

        String topicCF = null;
        String topicName = null;
        int rate = 0;
        if (args.length == 3) {
            topicCF = args[0];
            topicName = args[1];
            rate = Integer.parseInt(args[2]);
        } else {
            System.out.println("Invalid arguments. Should be: ");
            System.out.println("java TBorrower [factory] [topic] [rate]");
            System.exit(0);
        }

        TBorrower borrower = new TBorrower(topicCF, topicName, rate);

        try {
            // Run until enter is pressed
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(System.in));
            System.out.println ("TBorrower application started");
            System.out.println ("Press enter to quit application");
            reader.readLine();
            borrower.exit();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public TopicConnection gettConnection() {
        return tConnection;
    }

    public TopicSession gettSession() {
        return tSession;
    }

    public Topic getTopic() {
        return topic;
    }

    public double getCurrentRate() {
        return currentRate;
    }
}
```

## Class diagram
![alt text](./etc/publishsubscribe.urm.png "Publish Subscribe class diagram")

## Applicability

Use the Publish-Subscribe pattern when

* An application needs to broadcast information to a significant number of consumers.
* An application needs to communicate with one or more independently developed applications or services, which may use different platforms, programming languages, and communication protocols.
* An application needs to communicate information to multiple consumers, which may have different availability requirements or uptime schedules than the sender.

## Tutorials

* [Enterprise Integration Patterns](https://www.enterpriseintegrationpatterns.com/patterns/messaging/ObserverJmsExample.html)

## Consequences

Pros

* Pub-Sub activity is asynchronous (a.k.a, “fire and forget”). Hence, there is little risk of performance degradation due to a process getting caught in a long-running data exchange interaction.
* Pub-sub promotes loose coupling between publishers and subscribers. Publishers and subscribers don't need to know each other's details, allowing for greater flexibility in system design and easier component replacement or updates.
* Subscribers can dynamically subscribe and unsubscribe to topics based on their interests, allowing for dynamic adaptation to changing requirements or system conditions.
* Pub-sub is well-suited for building event-driven architectures, where components react to events by subscribing to relevant topics. This enables real-time processing, event propagation, and system-wide coordination.

Cons

* Messaging systems typically don't guarantee strict message ordering across subscribers. While messages within a single topic are usually delivered in order, there's no guarantee of global message ordering across topics or subscribers.
* Messaging introduces some latency compared to direct point-to-point communication, as messages need to be routed through the pub-sub system to reach subscribers. While typically minimal, this latency may be a consideration for latency-sensitive applications.
* messages may be lost if subscribers are not actively consuming messages or if there are network or system failures.

## Real-world examples

* Market Data Feeds
* Patient Monitoring in Healthcare
* Supply Chain Visibility in Logistics
* Communication between components in a distributed computer system

## Credits

* [Java Message Service, 2nd Edition](Author(s): Mark Richards, Richard Monson-Haefel, David A Chappell. Publisher(s): O'Reilly Media, Inc.)
* [Red Hat](https://www.redhat.com/architect/pub-sub-pros-and-cons)
* [Microsoft](https://learn.microsoft.com/en-us/azure/architecture/patterns/publisher-subscriber)