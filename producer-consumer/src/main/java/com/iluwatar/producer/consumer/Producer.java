package com.iluwatar.producer.consumer;

import java.util.Random;

/**
 * Class responsible for producing unit of work that can be expressed as {@link Item} and submitted
 * to queue
 */
public class Producer {

  private final ItemQueue queue;

  private final String name;

  private int itemId = 0;

  public Producer(String name, ItemQueue queue) {
    this.name = name;
    this.queue = queue;
  }

  /**
   * Put item in the queue
   */
  public void produce() throws InterruptedException {

    Item item = new Item(name, itemId++);
    queue.put(item);
    Random random = new Random();
    Thread.sleep(random.nextInt(2000));
  }
}
