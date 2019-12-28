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

package com.iluwatar.flux.dispatcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.iluwatar.flux.action.Action;
import com.iluwatar.flux.action.ActionType;
import com.iluwatar.flux.action.Content;
import com.iluwatar.flux.action.ContentAction;
import com.iluwatar.flux.action.MenuAction;
import com.iluwatar.flux.action.MenuItem;
import com.iluwatar.flux.store.Store;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

/**
 * Date: 12/12/15 - 8:22 PM
 *
 * @author Jeroen Meulemeester
 */
public class DispatcherTest {

  /**
   * Dispatcher is a singleton with no way to reset it's internal state back to the beginning.
   * Replace the instance with a fresh one before each test to make sure test cases have no
   * influence on each other.
   */
  @BeforeEach
  public void setUp() throws Exception {
    final var constructor = Dispatcher.class.getDeclaredConstructor();
    constructor.setAccessible(true);

    final var field = Dispatcher.class.getDeclaredField("instance");
    field.setAccessible(true);
    field.set(Dispatcher.getInstance(), constructor.newInstance());
  }

  @Test
  public void testGetInstance() {
    assertNotNull(Dispatcher.getInstance());
    assertSame(Dispatcher.getInstance(), Dispatcher.getInstance());
  }

  @Test
  public void testMenuItemSelected() {
    final var dispatcher = Dispatcher.getInstance();

    final var store = mock(Store.class);
    dispatcher.registerStore(store);
    dispatcher.menuItemSelected(MenuItem.HOME);
    dispatcher.menuItemSelected(MenuItem.COMPANY);

    // We expect 4 events, 2 menu selections and 2 content change actions
    final var actionCaptor = ArgumentCaptor.forClass(Action.class);
    verify(store, times(4)).onAction(actionCaptor.capture());
    verifyNoMoreInteractions(store);

    final var actions = actionCaptor.getAllValues();
    final var menuActions = actions.stream()
        .filter(a -> a.getType().equals(ActionType.MENU_ITEM_SELECTED))
        .map(a -> (MenuAction) a)
        .collect(Collectors.toList());

    final var contentActions = actions.stream()
        .filter(a -> a.getType().equals(ActionType.CONTENT_CHANGED))
        .map(a -> (ContentAction) a)
        .collect(Collectors.toList());

    assertEquals(2, menuActions.size());
    assertEquals(1, menuActions.stream().map(MenuAction::getMenuItem).filter(MenuItem.HOME::equals)
        .count());
    assertEquals(1, menuActions.stream().map(MenuAction::getMenuItem)
        .filter(MenuItem.COMPANY::equals).count());

    assertEquals(2, contentActions.size());
    assertEquals(1, contentActions.stream().map(ContentAction::getContent)
        .filter(Content.PRODUCTS::equals).count());
    assertEquals(1, contentActions.stream().map(ContentAction::getContent)
        .filter(Content.COMPANY::equals).count());

  }

}
