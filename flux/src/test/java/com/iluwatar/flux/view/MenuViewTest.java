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

package com.iluwatar.flux.view;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.iluwatar.flux.action.Action;
import com.iluwatar.flux.action.MenuItem;
import com.iluwatar.flux.dispatcher.Dispatcher;
import com.iluwatar.flux.store.MenuStore;
import com.iluwatar.flux.store.Store;
import org.junit.jupiter.api.Test;

/**
 * Date: 12/12/15 - 10:31 PM
 *
 * @author Jeroen Meulemeester
 */
public class MenuViewTest {

  @Test
  public void testStoreChanged() {
    final var store = mock(MenuStore.class);
    when(store.getSelected()).thenReturn(MenuItem.HOME);

    final var view = new MenuView();
    view.storeChanged(store);

    verify(store, times(1)).getSelected();
    verifyNoMoreInteractions(store);
  }

  @Test
  public void testItemClicked() {
    final var store = mock(Store.class);
    Dispatcher.getInstance().registerStore(store);

    final var view = new MenuView();
    view.itemClicked(MenuItem.PRODUCTS);

    // We should receive a menu click action and a content changed action
    verify(store, times(2)).onAction(any(Action.class));

  }

}
