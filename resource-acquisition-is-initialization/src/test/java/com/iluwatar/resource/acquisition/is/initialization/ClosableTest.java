package com.iluwatar.resource.acquisition.is.initialization;

import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.inOrder;

/**
 * Date: 12/28/15 - 9:31 PM
 *
 * @author Jeroen Meulemeester
 */
public class ClosableTest extends StdOutTest {

  @Test
  public void testOpenClose() throws Exception {
    final InOrder inOrder = inOrder(getStdOutMock());
    try (final SlidingDoor door = new SlidingDoor(); final TreasureChest chest = new TreasureChest()) {
      inOrder.verify(getStdOutMock()).println("Sliding door opens.");
      inOrder.verify(getStdOutMock()).println("Treasure chest opens.");
    }
    inOrder.verify(getStdOutMock()).println("Treasure chest closes.");
    inOrder.verify(getStdOutMock()).println("Sliding door closes.");
    inOrder.verifyNoMoreInteractions();
  }

}