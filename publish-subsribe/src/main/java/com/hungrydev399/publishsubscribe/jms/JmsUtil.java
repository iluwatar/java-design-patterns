package com.hungrydev399.publishsubscribe.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * JMS utility class that manages connections and provides an embedded message broker.
 * Supports both shared connections and client-specific connections for durable subscriptions.
 */
public final class JmsUtil {
  private static final String BROKER_URL = "tcp://localhost:61616";
  private static ConnectionFactory factory;
  private static Connection defaultConnection;
  private static BrokerService broker;
  private static Map<String, Connection> clientConnections = new ConcurrentHashMap<>();
  private static boolean isInitialized = false;

  private JmsUtil() {
    // Utility class, prevent instantiation
  }

  public static synchronized void initialize() {
    if (!isInitialized) {
      try {
        broker = new BrokerService();
        broker.addConnector(BROKER_URL);
        broker.setPersistent(false);
        broker.start();

        factory = new ActiveMQConnectionFactory(BROKER_URL);
        defaultConnection = factory.createConnection();
        defaultConnection.start();
        isInitialized = true;
      } catch (Exception e) {
        System.err.println("Failed to initialize JMS: " + e.getMessage());
        throw new RuntimeException(e);
      }
    }
  }

  /**
   * Creates a JMS session, optionally with a client ID for durable subscriptions.
   * Each client ID gets its own dedicated connection to support durable subscribers.
   */
  public static Session createSession(String clientId) throws JMSException {
    if (!isInitialized) {
      initialize();
    }
    if (clientId == null) {
      return defaultConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);
    }

    Connection conn = clientConnections.computeIfAbsent(clientId, id -> {
      try {
        Connection newConn = factory.createConnection();
        newConn.setClientID(id);
        newConn.start();
        return newConn;
      } catch (JMSException e) {
        throw new RuntimeException(e);
      }
    });

    return conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
  }

  public static Session createSession() throws JMSException {
    return createSession(null);
  }

  /**
   * Closes all JMS resources.
   */
  public static synchronized void closeConnection() {
    try {
      isInitialized = false;
      if (defaultConnection != null) {
        defaultConnection.close();
      }
      for (Connection conn : clientConnections.values()) {
        if (conn != null) {
          conn.close();
        }
      }
      clientConnections.clear();
      if (broker != null) {
        broker.stop();
      }
    } catch (Exception e) {
      System.err.println("Error closing JMS resources: " + e.getMessage());
    }
  }

  public static synchronized void reset() {
    closeConnection();
    isInitialized = false;
    initialize();
  }
}
