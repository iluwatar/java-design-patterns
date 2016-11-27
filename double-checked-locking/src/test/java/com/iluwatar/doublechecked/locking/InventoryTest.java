/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.doublechecked.locking;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Date: 12/10/15 - 9:34 PM
 *
 * @author Jeroen Meulemeester
 */
public class InventoryTest {

  private InMemoryAppender appender;

  @Before
  public void setUp() {
    appender = new InMemoryAppender(Inventory.class);
  }

  @After
  public void tearDown() {
    appender.stop();
  }

  /**
   * The number of threads used to stress test the locking of the {@link Inventory#addItem(Item)}
   * method
   */
  private static final int THREAD_COUNT = 8;

  /**
   * The maximum number of {@link Item}s allowed in the {@link Inventory}
   */
  private static final int INVENTORY_SIZE = 1000;

  /**
   * Concurrently add multiple items to the inventory, and check if the items were added in order by
   * checking the stdOut for continuous growth of the inventory. When 'items.size()=xx' shows up out
   * of order, it means that the locking is not ok, increasing the risk of going over the inventory
   * item limit.
   */
  @Test(timeout = 10000)
  public void testAddItem() throws Exception {
    // Create a new inventory with a limit of 1000 items and put some load on the add method
    final Inventory inventory = new Inventory(INVENTORY_SIZE);
    final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);
    for (int i = 0; i < THREAD_COUNT; i++) {
      executorService.execute(() -> {
        while (inventory.addItem(new Item())) {};
      });
    }

    // Wait until all threads have finished
    executorService.shutdown();
    executorService.awaitTermination(5, TimeUnit.SECONDS);

    // Check the number of items in the inventory. It should not have exceeded the allowed maximum
    final List<Item> items = inventory.getItems();
    assertNotNull(items);
    assertEquals(INVENTORY_SIZE, items.size());

    assertEquals(INVENTORY_SIZE, appender.getLogSize());

    // ... and check if the inventory size is increasing continuously
    for (int i = 0; i < items.size(); i++) {
      assertTrue(appender.log.get(i).getFormattedMessage().contains("items.size()=" + (i + 1)));
    }
  }



  private class InMemoryAppender extends AppenderBase<ILoggingEvent> {
    private List<ILoggingEvent> log = new LinkedList<>();

    public InMemoryAppender(Class clazz) {
      ((Logger) LoggerFactory.getLogger(clazz)).addAppender(this);
      start();
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
      log.add(eventObject);
    }

    public int getLogSize() {
      return log.size();
    }
  }

}
