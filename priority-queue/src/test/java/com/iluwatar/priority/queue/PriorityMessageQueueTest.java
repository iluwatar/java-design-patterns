package com.iluwatar.priority.queue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PriorityMessageQueueTest {

  @Before
  public void setUp() throws Exception {

  }

  @Test
  public void remove() {
    PriorityMessageQueue<String> stringPriorityMessageQueue = new PriorityMessageQueue<>(new String[2]);
    String pushMessage = "test";
    stringPriorityMessageQueue.add(pushMessage);
    Assert.assertEquals(stringPriorityMessageQueue.remove(), pushMessage);
  }

  @Test
  public void add() {
    PriorityMessageQueue<Integer> stringPriorityMessageQueue = new PriorityMessageQueue<>(new Integer[2]);
    stringPriorityMessageQueue.add(1);
    stringPriorityMessageQueue.add(5);
    stringPriorityMessageQueue.add(10);
    stringPriorityMessageQueue.add(3);
    Assert.assertTrue(stringPriorityMessageQueue.remove() == 10);
  }

  @Test
  public void isEmpty() {
    PriorityMessageQueue<Integer> stringPriorityMessageQueue = new PriorityMessageQueue<>(new Integer[2]);
    Assert.assertTrue(stringPriorityMessageQueue.isEmpty());
    stringPriorityMessageQueue.add(1);
    stringPriorityMessageQueue.remove();
    Assert.assertTrue(stringPriorityMessageQueue.isEmpty());
  }

  @Test
  public void testEnsureSize() {
    PriorityMessageQueue<Integer> stringPriorityMessageQueue = new PriorityMessageQueue<>(new Integer[2]);
    Assert.assertTrue(stringPriorityMessageQueue.isEmpty());
    stringPriorityMessageQueue.add(1);
    stringPriorityMessageQueue.add(2);
    stringPriorityMessageQueue.add(2);
    stringPriorityMessageQueue.add(3);
    Assert.assertTrue(stringPriorityMessageQueue.remove() == 3);
  }
}