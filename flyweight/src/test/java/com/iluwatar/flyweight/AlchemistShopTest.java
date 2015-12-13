package com.iluwatar.flyweight;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Date: 12/12/15 - 10:54 PM
 *
 * @author Jeroen Meulemeester
 */
public class AlchemistShopTest {

  @Test
  public void testShop() throws Exception {
    final AlchemistShop shop = new AlchemistShop();

    final List<Potion> bottomShelf = shop.getBottomShelf();
    assertNotNull(bottomShelf);
    assertEquals(5, bottomShelf.size());

    final List<Potion> topShelf = shop.getTopShelf();
    assertNotNull(topShelf);
    assertEquals(8, topShelf.size());

    final List<Potion> allPotions = new ArrayList<>();
    allPotions.addAll(topShelf);
    allPotions.addAll(bottomShelf);

    // There are 13 potion instances, but only 5 unique instance types
    assertEquals(13, allPotions.size());
    assertEquals(5, allPotions.stream().map(System::identityHashCode).distinct().count());

  }

}
