package com.iluwatar.privateclassdata;

import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.inOrder;

/**
 * Date: 12/27/15 - 10:46 PM
 *
 * @author Jeroen Meulemeester
 */
public class StewTest extends StdOutTest {

  /**
   * Verify if mixing the stew doesn't change the internal state
   */
  @Test
  public void testMix() {
    final ImmutableStew stew = new ImmutableStew(1, 2, 3, 4);
    final String expectedMessage = "Mixing the immutable stew we find: 1 potatoes, "
        + "2 carrots, 3 meat and 4 peppers";

    final InOrder inOrder = inOrder(getStdOutMock());
    for (int i = 0; i < 20; i++) {
      stew.mix();
      inOrder.verify(getStdOutMock()).println(expectedMessage);
    }

    inOrder.verifyNoMoreInteractions();
  }

}