package com.iluwatar.priority.queue;

import org.junit.Assert;
import org.junit.Test;


public class QueueManagerTest {

  @Test
  public void publishMessage() {
    QueueManager queueManager = new QueueManager(2);
    Message testMessage = new Message("Test Message", 1);
    queueManager.publishMessage(testMessage);
    Message recivedMessage = queueManager.receiveMessage();
    Assert.assertEquals(testMessage, recivedMessage);
  }

  @Test
  public void receiveMessage() {
    QueueManager queueManager = new QueueManager(2);
    Message testMessage1 = new Message("Test Message 1", 1);
    queueManager.publishMessage(testMessage1);
    Message testMessage2 = new Message("Test Message 2", 2);
    queueManager.publishMessage(testMessage2);
    Message recivedMessage = queueManager.receiveMessage();
    Assert.assertEquals(testMessage2, recivedMessage);
  }
}