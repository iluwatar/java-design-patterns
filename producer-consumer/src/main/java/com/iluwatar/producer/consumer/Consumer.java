package com.iluwatar.producer.consumer;

/**
 * Class responsible for consume the {@link Item} produced by {@link Producer}
 */
public class Consumer {

  private final ItemQueue queue;

  private final String name;

  public Consumer(String name, ItemQueue queue) {
    this.name = name;
    this.queue = queue;
  }

  /**
   * Consume item from the queue
   */
  public void consume() throws InterruptedException {

    Item item = queue.take();
    System.out.println(String.format("Consumer [%s] consume item [%s] produced by [%s]", name,
        item.getId(), item.getProducer()));

  }
}
