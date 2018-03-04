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
package com.iluwatar.flux.view;

import com.iluwatar.flux.action.MenuItem;
import com.iluwatar.flux.dispatcher.Dispatcher;
import com.iluwatar.flux.store.MenuStore;
import com.iluwatar.flux.store.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * MenuView is a concrete view.
 *
 */
public class MenuView implements View {

  private static final Logger LOGGER = LoggerFactory.getLogger(MenuView.class);

  private MenuItem selected = MenuItem.HOME;

  @Override
  public void storeChanged(Store store) {
    MenuStore menuStore = (MenuStore) store;
    selected = menuStore.getSelected();
    render();
  }

  @Override
  public void render() {
    for (MenuItem item : MenuItem.values()) {
      if (selected.equals(item)) {
        LOGGER.info("* {}", item);
      } else {
        LOGGER.info(item.toString());
      }
    }
  }

  public void itemClicked(MenuItem item) {
    Dispatcher.getInstance().menuItemSelected(item);
  }
}
