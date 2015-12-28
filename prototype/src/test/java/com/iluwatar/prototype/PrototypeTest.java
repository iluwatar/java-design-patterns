package com.iluwatar.prototype;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

/**
 * Date: 12/28/15 - 8:45 PM
 *
 * @author Jeroen Meulemeester
 */
@RunWith(Parameterized.class)
public class PrototypeTest<P extends Prototype> {

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(
            new Object[]{new OrcBeast(), "Orcish wolf"},
            new Object[]{new OrcMage(), "Orcish mage"},
            new Object[]{new OrcWarlord(), "Orcish warlord"},
            new Object[]{new ElfBeast(), "Elven eagle"},
            new Object[]{new ElfMage(), "Elven mage"},
            new Object[]{new ElfWarlord(), "Elven warlord"}
    );
  }

  /**
   * The tested prototype instance
   */
  private final Prototype testedPrototype;

  /**
   * The expected {@link Prototype#toString()} value
   */
  private final String expectedToString;

  /**
   * Create a new test instance, using the given test object and expected value
   *
   * @param testedPrototype  The tested prototype instance
   * @param expectedToString The expected {@link Prototype#toString()} value
   */
  public PrototypeTest(final Prototype testedPrototype, final String expectedToString) {
    this.expectedToString = expectedToString;
    this.testedPrototype = testedPrototype;
  }

  @Test
  public void testPrototype() throws Exception {
    assertEquals(this.expectedToString, this.testedPrototype.toString());

    final Object clone = this.testedPrototype.clone();
    assertNotNull(clone);
    assertNotSame(clone, this.testedPrototype);
    assertSame(this.testedPrototype.getClass(), clone.getClass());
  }

}
