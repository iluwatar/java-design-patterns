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
package com.iluwatar.fluentinterface.fluentiterable;

import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Date: 12/12/15 - 7:00 PM
 *
 * @author Jeroen Meulemeester
 */
public abstract class FluentIterableTest {

  /**
   * Create a new {@link FluentIterable} from the given integers
   *
   * @param integers The integers
   * @return The new iterable, use for testing
   */
  protected abstract FluentIterable<Integer> createFluentIterable(final Iterable<Integer> integers);

  @Test
  public void testFirst() throws Exception {
    final List<Integer> integers = Arrays.asList(1, 2, 3, 10, 9, 8);
    final Optional<Integer> first = createFluentIterable(integers).first();
    assertNotNull(first);
    assertTrue(first.isPresent());
    assertEquals(integers.get(0), first.get());
  }

  @Test
  public void testFirstEmptyCollection() throws Exception {
    final List<Integer> integers = Collections.<Integer>emptyList();
    final Optional<Integer> first = createFluentIterable(integers).first();
    assertNotNull(first);
    assertFalse(first.isPresent());
  }

  @Test
  public void testFirstCount() throws Exception {
    final List<Integer> integers = Arrays.asList(1, 2, 3, 10, 9, 8);
    final List<Integer> first4 = createFluentIterable(integers)
            .first(4)
            .asList();

    assertNotNull(first4);
    assertEquals(4, first4.size());

    assertEquals(integers.get(0), first4.get(0));
    assertEquals(integers.get(1), first4.get(1));
    assertEquals(integers.get(2), first4.get(2));
    assertEquals(integers.get(3), first4.get(3));
  }

  @Test
  public void testFirstCountLessItems() throws Exception {
    final List<Integer> integers = Arrays.asList(1, 2, 3);
    final List<Integer> first4 = createFluentIterable(integers)
            .first(4)
            .asList();

    assertNotNull(first4);
    assertEquals(3, first4.size());

    assertEquals(integers.get(0), first4.get(0));
    assertEquals(integers.get(1), first4.get(1));
    assertEquals(integers.get(2), first4.get(2));
  }

  @Test
  public void testLast() throws Exception {
    final List<Integer> integers = Arrays.asList(1, 2, 3, 10, 9, 8);
    final Optional<Integer> last = createFluentIterable(integers).last();
    assertNotNull(last);
    assertTrue(last.isPresent());
    assertEquals(integers.get(integers.size() - 1), last.get());
  }

  @Test
  public void testLastEmptyCollection() throws Exception {
    final List<Integer> integers = Collections.<Integer>emptyList();
    final Optional<Integer> last = createFluentIterable(integers).last();
    assertNotNull(last);
    assertFalse(last.isPresent());
  }

  @Test
  public void testLastCount() throws Exception {
    final List<Integer> integers = Arrays.asList(1, 2, 3, 10, 9, 8);
    final List<Integer> last4 = createFluentIterable(integers)
            .last(4)
            .asList();

    assertNotNull(last4);
    assertEquals(4, last4.size());
    assertEquals(Integer.valueOf(3), last4.get(0));
    assertEquals(Integer.valueOf(10), last4.get(1));
    assertEquals(Integer.valueOf(9), last4.get(2));
    assertEquals(Integer.valueOf(8), last4.get(3));
  }

  @Test
  public void testLastCountLessItems() throws Exception {
    final List<Integer> integers = Arrays.asList(1, 2, 3);
    final List<Integer> last4 = createFluentIterable(integers)
            .last(4)
            .asList();

    assertNotNull(last4);
    assertEquals(3, last4.size());

    assertEquals(Integer.valueOf(1), last4.get(0));
    assertEquals(Integer.valueOf(2), last4.get(1));
    assertEquals(Integer.valueOf(3), last4.get(2));
  }

  @Test
  public void testFilter() throws Exception {
    final List<Integer> integers = Arrays.asList(1, 2, 3, 10, 9, 8);
    final List<Integer> evenItems = createFluentIterable(integers)
            .filter(i -> i % 2 == 0)
            .asList();

    assertNotNull(evenItems);
    assertEquals(3, evenItems.size());
    assertEquals(Integer.valueOf(2), evenItems.get(0));
    assertEquals(Integer.valueOf(10), evenItems.get(1));
    assertEquals(Integer.valueOf(8), evenItems.get(2));
  }

  @Test
  public void testMap() throws Exception {
    final List<Integer> integers = Arrays.asList(1, 2, 3);
    final List<Long> longs = createFluentIterable(integers)
            .map(Integer::longValue)
            .asList();

    assertNotNull(longs);
    assertEquals(integers.size(), longs.size());
    assertEquals(Long.valueOf(1), longs.get(0));
    assertEquals(Long.valueOf(2), longs.get(1));
    assertEquals(Long.valueOf(3), longs.get(2));
  }

  @Test
  public void testForEach() throws Exception {
    final List<Integer> integers = Arrays.asList(1, 2, 3);

    final Consumer<Integer> consumer = mock(Consumer.class);
    createFluentIterable(integers).forEach(consumer);

    verify(consumer, times(1)).accept(Integer.valueOf(1));
    verify(consumer, times(1)).accept(Integer.valueOf(2));
    verify(consumer, times(1)).accept(Integer.valueOf(3));
    verifyNoMoreInteractions(consumer);

  }

  @Test
  public void testSpliterator() throws Exception {
    final List<Integer> integers = Arrays.asList(1, 2, 3);
    final Spliterator<Integer> split = createFluentIterable(integers).spliterator();
    assertNotNull(split);
  }

}