package com.iluwatar.lazy.loading;

import java.lang.reflect.Field;

/**
 * Date: 12/19/15 - 12:19 PM
 *
 * @author Jeroen Meulemeester
 */
public class HolderThreadSafeTest extends AbstractHolderTest {

  private final HolderThreadSafe holder = new HolderThreadSafe();

  @Override
  Heavy getInternalHeavyValue() throws Exception {
    final Field holderField = HolderThreadSafe.class.getDeclaredField("heavy");
    holderField.setAccessible(true);
    return (Heavy) holderField.get(this.holder);
  }

  @Override
  Heavy getHeavy() throws Exception {
    return this.holder.getHeavy();
  }

}