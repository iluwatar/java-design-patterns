package com.iluwatar.producer.consumer;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * Class as a channel for {@link Producer}-{@link Consumer} exchange.
 */
public class ItemQueue {

  private LinkedBlockingQueue<Item> queue;

  public ItemQueue() {

    queue = new LinkedBlockingQueue<>(5);
  }

  public void put(Item item) throws InterruptedException {

    queue.put(item);
  }

  public Item take() throws InterruptedException {

    return queue.take();
  }

}
