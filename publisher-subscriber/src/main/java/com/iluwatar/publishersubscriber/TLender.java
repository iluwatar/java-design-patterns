package com.iluwatar.publishersubscriber;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.broker.BrokerService;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
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

    TLender tLender = new TLender(topicCFName, topicName);

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
          tLender.exit();
        }
        else { //publish the entered rate
          double newRate = Double.parseDouble(line);
          tLender.publishRate(newRate);
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
