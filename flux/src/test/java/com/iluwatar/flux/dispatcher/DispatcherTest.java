package com.iluwatar.flux.dispatcher;

import com.iluwatar.flux.action.Action;
import com.iluwatar.flux.action.ActionType;
import com.iluwatar.flux.action.Content;
import com.iluwatar.flux.action.ContentAction;
import com.iluwatar.flux.action.MenuAction;
import com.iluwatar.flux.action.MenuItem;
import com.iluwatar.flux.store.Store;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

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
  @Before
  public void setUp() throws Exception {
    final Constructor<Dispatcher> constructor;
    constructor = Dispatcher.class.getDeclaredConstructor();
    constructor.setAccessible(true);

    final Field field = Dispatcher.class.getDeclaredField("instance");
    field.setAccessible(true);
    field.set(Dispatcher.getInstance(), constructor.newInstance());
  }

  @Test
  public void testGetInstance() throws Exception {
    assertNotNull(Dispatcher.getInstance());
    assertSame(Dispatcher.getInstance(), Dispatcher.getInstance());
  }

  @Test
  public void testMenuItemSelected() throws Exception {
    final Dispatcher dispatcher = Dispatcher.getInstance();

    final Store store = mock(Store.class);
    dispatcher.registerStore(store);
    dispatcher.menuItemSelected(MenuItem.HOME);
    dispatcher.menuItemSelected(MenuItem.COMPANY);

    // We expect 4 events, 2 menu selections and 2 content change actions
    final ArgumentCaptor<Action> actionCaptor = ArgumentCaptor.forClass(Action.class);
    verify(store, times(4)).onAction(actionCaptor.capture());
    verifyNoMoreInteractions(store);

    final List<Action> actions = actionCaptor.getAllValues();
    final List<MenuAction> menuActions = actions.stream()
            .filter(a -> a.getType().equals(ActionType.MENU_ITEM_SELECTED))
            .map(a -> (MenuAction) a)
            .collect(Collectors.toList());

    final List<ContentAction> contentActions = actions.stream()
            .filter(a -> a.getType().equals(ActionType.CONTENT_CHANGED))
            .map(a -> (ContentAction) a)
            .collect(Collectors.toList());

    assertEquals(2, menuActions.size());
    assertEquals(1, menuActions.stream().map(MenuAction::getMenuItem).filter(MenuItem.HOME::equals).count());
    assertEquals(1, menuActions.stream().map(MenuAction::getMenuItem).filter(MenuItem.COMPANY::equals).count());

    assertEquals(2, contentActions.size());
    assertEquals(1, contentActions.stream().map(ContentAction::getContent).filter(Content.PRODUCTS::equals).count());
    assertEquals(1, contentActions.stream().map(ContentAction::getContent).filter(Content.COMPANY::equals).count());

  }

}
