package com.iluwatar.lazy.loading;

import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertSame;
import static junit.framework.TestCase.assertNull;

/**
 * Date: 12/19/15 - 11:58 AM
 *
 * @author Jeroen Meulemeester
 */
public abstract class AbstractHolderTest {

  /**
   * Get the internal state of the holder value
   *
   * @return The internal value
   */
  abstract Heavy getInternalHeavyValue() throws Exception;

  /**
   * Request a lazy loaded {@link Heavy} object from the holder.
   *
   * @return The lazy loaded {@link Heavy} object
   */
  abstract Heavy getHeavy() throws Exception;

  /**
   * This test shows that the heavy field is not instantiated until the method getHeavy is called
   */
  @Test(timeout = 3000)
  public void testGetHeavy() throws Exception {
    assertNull(getInternalHeavyValue());
    assertNotNull(getHeavy());
    assertNotNull(getInternalHeavyValue());
    assertSame(getHeavy(), getInternalHeavyValue());
  }

}
