package com.iluwatar.publishsubscribe;

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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

@Slf4j
public class Lender {

  private TopicConnection tConnection;
  private TopicSession tSession;
  private Topic topic;
  private TopicPublisher publisher;
  private static final String ERROR = "An error has occured!";

  public Lender(String topicCFName, String topicName) {

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
      LOGGER.error(ERROR, e);
    } catch(JMSException e) {
      LOGGER.error(ERROR, e);
    }
  }

  public void publishRate(double newRate) {

    try {
      //create JMS message
      BytesMessage message = tSession.createBytesMessage();
      message.writeDouble(newRate);

      //publish message
      publisher.publish(message);
    } catch(JMSException e) {
      LOGGER.error(ERROR, e);
    }
  }

  public boolean close() {
    try {
      tConnection.close();
      return true;
    } catch(JMSException e) {
      LOGGER.error(ERROR, e);
      return false;
    }
  }

  public static void main(String[] args) {

    String topicCFName = null;
    String topicName = null;

    if(args.length == 2) {
      topicCFName = args[0];
      topicName = args[1];
    } else {
      LOGGER.info("Invalid arguments. Should be: ");
      LOGGER.info("java TLender [factory] [topic]");
      System.exit(1);
    }

    try {
      //Get configuration properties
      Properties props = new Properties();
      InputStream in = new FileInputStream("publish-subscribe/src/main/resources/config.properties");
      props.load(in);
      in.close();

      // Create and start activeMQ broker. Broker decouples publishers and subscribers.
      // Additionally brokers manage threads and asynchronous sending and receiving of messages.
      BrokerService broker = new BrokerService();
      broker.addConnector(props.getProperty("ADDRESS"));
      broker.start();

    } catch(FileNotFoundException e) {
      LOGGER.error(ERROR, e);
    } catch(IOException e) {
      LOGGER.error(ERROR, e);
    } catch(Exception e) {
      LOGGER.error(ERROR, e);
    }

    Lender lender = new Lender(topicCFName, topicName);

    LOGGER.info("TLender Application Started");
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
          lender.close();
          System.exit(0);
        }
        else { //publish the entered rate
          double newRate = Double.parseDouble(line);
          lender.publishRate(newRate);
        }
      }
    } catch(IOException e) {
      LOGGER.error(ERROR, e);
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
