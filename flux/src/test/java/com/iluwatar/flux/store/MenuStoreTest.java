/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.flux.store;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

import com.iluwatar.flux.action.Content;
import com.iluwatar.flux.action.ContentAction;
import com.iluwatar.flux.action.MenuAction;
import com.iluwatar.flux.action.MenuItem;
import com.iluwatar.flux.view.View;
import org.junit.jupiter.api.Test;

/**
 * Date: 12/12/15 - 10:18 PM
 *
 * @author Jeroen Meulemeester
 */
public class MenuStoreTest {

  @Test
  void testOnAction() {
    final var menuStore = new MenuStore();

    final var view = mock(View.class);
    menuStore.registerView(view);

    verifyZeroInteractions(view);

    // Menu should not react on content action ...
    menuStore.onAction(new ContentAction(Content.COMPANY));
    verifyZeroInteractions(view);

    // ... but it should react on a menu action
    menuStore.onAction(new MenuAction(MenuItem.PRODUCTS));
    verify(view, times(1)).storeChanged(eq(menuStore));
    verifyNoMoreInteractions(view);
    assertEquals(MenuItem.PRODUCTS, menuStore.getSelected());

  }

}
