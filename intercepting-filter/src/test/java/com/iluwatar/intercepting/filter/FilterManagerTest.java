package com.iluwatar.intercepting.filter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Date: 12/13/15 - 3:01 PM
 *
 * @author Jeroen Meulemeester
 */
public class FilterManagerTest {

  @Test
  public void testFilterRequest() throws Exception {
    final Target target = mock(Target.class);
    final FilterManager filterManager = new FilterManager(target);
    assertEquals("RUNNING...", filterManager.filterRequest(mock(Order.class)));
    verifyZeroInteractions(target);
  }

  @Test
  public void testAddFilter() throws Exception {
    final Target target = mock(Target.class);
    final FilterManager filterManager = new FilterManager(target);

    verifyZeroInteractions(target);

    final Filter filter = mock(Filter.class);
    when(filter.execute(any(Order.class))).thenReturn("filter");

    filterManager.addFilter(filter);

    final Order order = mock(Order.class);
    assertEquals("filter", filterManager.filterRequest(order));

    verify(filter, times(1)).execute(any(Order.class));
    verifyZeroInteractions(target, filter, order);
  }
}