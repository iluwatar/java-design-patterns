package com.iluwatar.lazy.loading;

import java.lang.reflect.Field;
import java.util.function.Supplier;

/**
 * Date: 12/19/15 - 12:27 PM
 *
 * @author Jeroen Meulemeester
 */
public class Java8HolderTest extends AbstractHolderTest {

  private final Java8Holder holder = new Java8Holder();


  @Override
  Heavy getInternalHeavyValue() throws Exception {
    final Field holderField = Java8Holder.class.getDeclaredField("heavy");
    holderField.setAccessible(true);

    final Supplier<Heavy> supplier = (Supplier<Heavy>) holderField.get(this.holder);
    final Class<? extends Supplier> supplierClass = supplier.getClass();

    // This is a little fishy, but I don't know another way to test this:
    // The lazy holder is at first a lambda, but gets replaced with a new supplier after loading ...
    if (supplierClass.isLocalClass()) {
      final Field instanceField = supplierClass.getDeclaredField("heavyInstance");
      instanceField.setAccessible(true);
      return (Heavy) instanceField.get(supplier);
    } else {
      return null;
    }
  }

  @Override
  Heavy getHeavy() throws Exception {
    return holder.getHeavy();
  }

}