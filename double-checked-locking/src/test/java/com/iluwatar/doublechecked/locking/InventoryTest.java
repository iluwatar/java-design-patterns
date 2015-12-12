package com.iluwatar.doublechecked.locking;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Date: 12/10/15 - 9:34 PM
 *
 * @author Jeroen Meulemeester
 */
public class InventoryTest {

  /**
   * The mocked standard out {@link PrintStream}, used to verify a steady increasing size of the
   * {@link Inventory} while adding items from multiple threads concurrently
   */
  private final PrintStream stdOutMock = mock(PrintStream.class);

  /**
   * Keep the original std-out so it can be restored after the test
   */
  private final PrintStream stdOutOrig = System.out;

  /**
   * Inject the mocked std-out {@link PrintStream} into the {@link System} class before each test
   */
  @Before
  public void setUp() {
    System.setOut(this.stdOutMock);
  }

  /**
   * Removed the mocked std-out {@link PrintStream} again from the {@link System} class
   */
  @After
  public void tearDown() {
    System.setOut(this.stdOutOrig);
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
        while (inventory.addItem(new Item())) ;
      });
    }

    // Wait until all threads have finished
    executorService.shutdown();
    executorService.awaitTermination(5, TimeUnit.SECONDS);

    // Check the number of items in the inventory. It should not have exceeded the allowed maximum
    final List<Item> items = inventory.getItems();
    assertNotNull(items);
    assertEquals(INVENTORY_SIZE, items.size());

    // Capture all stdOut messages ...
    final ArgumentCaptor<String> stdOutCaptor = ArgumentCaptor.forClass(String.class);
    verify(this.stdOutMock, times(INVENTORY_SIZE)).println(stdOutCaptor.capture());

    // ... verify if we got all 1000
    final List<String> values = stdOutCaptor.getAllValues();
    assertEquals(INVENTORY_SIZE, values.size());

    // ... and check if the inventory size is increasing continuously
    for (int i = 0; i < values.size(); i++) {
      assertNotNull(values.get(i));
      assertTrue(values.get(i).contains("items.size()=" + (i + 1)));
    }

    verifyNoMoreInteractions(this.stdOutMock);
  }

}