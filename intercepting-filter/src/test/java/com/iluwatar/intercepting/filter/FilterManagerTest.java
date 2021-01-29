/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
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

package com.iluwatar.intercepting.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

/**
 * Date: 12/13/15 - 3:01 PM
 *
 * @author Jeroen Meulemeester
 */
public class FilterManagerTest {

  @Test
  public void testFilterRequest() {
    final var target = mock(Target.class);
    final var filterManager = new FilterManager();
    assertEquals("RUNNING...", filterManager.filterRequest(mock(Order.class)));
    verifyZeroInteractions(target);
  }

  @Test
  public void testAddFilter() {
    final var target = mock(Target.class);
    final var filterManager = new FilterManager();

    verifyZeroInteractions(target);

    final var filter = mock(Filter.class);
    when(filter.execute(any(Order.class))).thenReturn("filter");

    filterManager.addFilter(filter);

    final Order order = mock(Order.class);
    assertEquals("filter", filterManager.filterRequest(order));

    verify(filter, times(1)).execute(any(Order.class));
    verifyZeroInteractions(target, filter, order);
  }
}