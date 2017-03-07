/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.object.pool;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Date: 12/27/15 - 1:05 AM
 *
 * @author Jeroen Meulemeester
 */
public class OliphauntPoolTest {

  /**
   * Use the same object 100 times subsequently. This should not take much time since the heavy
   * object instantiation is done only once. Verify if we get the same object each time.
   */
  @Test(timeout = 5000)
  public void testSubsequentCheckinCheckout() {
    final OliphauntPool pool = new OliphauntPool();
    assertEquals(pool.toString(), "Pool available=0 inUse=0");

    final Oliphaunt expectedOliphaunt = pool.checkOut();
    assertEquals(pool.toString(), "Pool available=0 inUse=1");

    pool.checkIn(expectedOliphaunt);
    assertEquals(pool.toString(), "Pool available=1 inUse=0");

    for (int i = 0; i < 100; i++) {
      final Oliphaunt oliphaunt = pool.checkOut();
      assertEquals(pool.toString(), "Pool available=0 inUse=1");
      assertSame(expectedOliphaunt, oliphaunt);
      assertEquals(expectedOliphaunt.getId(), oliphaunt.getId());
      assertEquals(expectedOliphaunt.toString(), oliphaunt.toString());

      pool.checkIn(oliphaunt);
      assertEquals(pool.toString(), "Pool available=1 inUse=0");
    }

  }

  /**
   * Use the same object 100 times subsequently. This should not take much time since the heavy
   * object instantiation is done only once. Verify if we get the same object each time.
   */
  @Test(timeout = 5000)
  public void testConcurrentCheckinCheckout() {
    final OliphauntPool pool = new OliphauntPool();
    assertEquals(pool.toString(), "Pool available=0 inUse=0");

    final Oliphaunt firstOliphaunt = pool.checkOut();
    assertEquals(pool.toString(), "Pool available=0 inUse=1");

    final Oliphaunt secondOliphaunt = pool.checkOut();
    assertEquals(pool.toString(), "Pool available=0 inUse=2");

    assertNotSame(firstOliphaunt, secondOliphaunt);
    assertEquals(firstOliphaunt.getId() + 1, secondOliphaunt.getId());

    // After checking in the second, we should get the same when checking out a new oliphaunt ...
    pool.checkIn(secondOliphaunt);
    assertEquals(pool.toString(), "Pool available=1 inUse=1");

    final Oliphaunt oliphaunt3 = pool.checkOut();
    assertEquals(pool.toString(), "Pool available=0 inUse=2");
    assertSame(secondOliphaunt, oliphaunt3);

    // ... and the same applies for the first one
    pool.checkIn(firstOliphaunt);
    assertEquals(pool.toString(), "Pool available=1 inUse=1");

    final Oliphaunt oliphaunt4 = pool.checkOut();
    assertEquals(pool.toString(), "Pool available=0 inUse=2");
    assertSame(firstOliphaunt, oliphaunt4);

    // When both oliphaunt return to the pool, we should still get the same instances
    pool.checkIn(firstOliphaunt);
    assertEquals(pool.toString(), "Pool available=1 inUse=1");

    pool.checkIn(secondOliphaunt);
    assertEquals(pool.toString(), "Pool available=2 inUse=0");

    // The order of the returned instances is not determined, so just put them in a list
    // and verify if both expected instances are in there.
    final List<Oliphaunt> oliphaunts = Arrays.asList(pool.checkOut(), pool.checkOut());
    assertEquals(pool.toString(), "Pool available=0 inUse=2");
    assertTrue(oliphaunts.contains(firstOliphaunt));
    assertTrue(oliphaunts.contains(secondOliphaunt));

  }


}