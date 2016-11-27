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
package com.iluwatar.flux.dispatcher;

import java.util.LinkedList;
import java.util.List;

import com.iluwatar.flux.action.Action;
import com.iluwatar.flux.action.Content;
import com.iluwatar.flux.action.ContentAction;
import com.iluwatar.flux.action.MenuAction;
import com.iluwatar.flux.action.MenuItem;
import com.iluwatar.flux.store.Store;

/**
 * 
 * Dispatcher sends Actions to registered Stores.
 *
 */
public final class Dispatcher {

  private static Dispatcher instance = new Dispatcher();

  private List<Store> stores = new LinkedList<>();

  private Dispatcher() {}

  public static Dispatcher getInstance() {
    return instance;
  }

  public void registerStore(Store store) {
    stores.add(store);
  }

  /**
   * Menu item selected handler
   */
  public void menuItemSelected(MenuItem menuItem) {
    dispatchAction(new MenuAction(menuItem));
    switch (menuItem) {
      case HOME:
      case PRODUCTS:
      default:
        dispatchAction(new ContentAction(Content.PRODUCTS));
        break;
      case COMPANY:
        dispatchAction(new ContentAction(Content.COMPANY));
        break;
    }
  }

  private void dispatchAction(Action action) {
    stores.stream().forEach(store -> store.onAction(action));
  }
}
