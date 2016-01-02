package com.iluwatar.multiton;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

/**
 * Date: 12/22/15 - 22:28 AM
 *
 * @author Jeroen Meulemeester
 */
public class NazgulTest {

  /**
   * Verify if {@link Nazgul#getInstance(NazgulName)} returns the correct Nazgul multiton instance
   */
  @Test
  public void testGetInstance() {
    for (final NazgulName name : NazgulName.values()) {
      final Nazgul nazgul = Nazgul.getInstance(name);
      assertNotNull(nazgul);
      assertSame(nazgul, Nazgul.getInstance(name));
      assertEquals(name, nazgul.getName());
    }
  }

}
