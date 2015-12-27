package com.iluwatar.producer.consumer;

import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;

/**
 * Date: 12/27/15 - 11:01 PM
 *
 * @author Jeroen Meulemeester
 */
public class ConsumerTest extends StdOutTest {

  private static final int ITEM_COUNT = 5;

  @Test
  public void testConsume() throws Exception {
    final ItemQueue queue = spy(new ItemQueue());
    for (int id = 0; id < ITEM_COUNT; id++) {
      queue.put(new Item("producer", id));
    }

    reset(queue); // Don't count the preparation above as interactions with the queue
    final Consumer consumer = new Consumer("consumer", queue);

    final InOrder inOrder = inOrder(getStdOutMock());
    for (int id = 0; id < ITEM_COUNT; id++) {
      consumer.consume();
      inOrder.verify(getStdOutMock())
              .println("Consumer [consumer] consume item [" + id + "] produced by [producer]");
    }

    inOrder.verifyNoMoreInteractions();
  }

}
