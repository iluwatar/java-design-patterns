package com.iluwatar.privateclassdata;

import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;

/**
 * Date: 12/27/15 - 10:46 PM
 *
 * @author Jeroen Meulemeester
 */
public class ImmutableStewTest extends StdOutTest {

  /**
   * Verify if mixing the stew doesn't change the internal state
   */
  @Test
  public void testMix() {
    final Stew stew = new Stew(1, 2, 3, 4);
    final String message = "Mixing the stew we find: 1 potatoes, 2 carrots, 3 meat and 4 peppers";

    final InOrder inOrder = inOrder(getStdOutMock());
    for (int i = 0; i < 20; i++) {
      stew.mix();
      inOrder.verify(getStdOutMock()).println(message);
    }

    inOrder.verifyNoMoreInteractions();
  }

  /**
   * Verify if tasting the stew actually removes one of each ingredient
   */
  @Test
  public void testDrink() {
    final Stew stew = new Stew(1, 2, 3, 4);
    stew.mix();

    verify(getStdOutMock())
            .println("Mixing the stew we find: 1 potatoes, 2 carrots, 3 meat and 4 peppers");

    stew.taste();
    verify(getStdOutMock()).println("Tasting the stew");

    stew.mix();
    verify(getStdOutMock())
            .println("Mixing the stew we find: 0 potatoes, 1 carrots, 2 meat and 3 peppers");

  }
}