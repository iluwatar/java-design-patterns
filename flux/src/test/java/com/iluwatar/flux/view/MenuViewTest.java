package com.iluwatar.flux.view;

import com.iluwatar.flux.action.Action;
import com.iluwatar.flux.action.MenuItem;
import com.iluwatar.flux.dispatcher.Dispatcher;
import com.iluwatar.flux.store.MenuStore;
import com.iluwatar.flux.store.Store;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * Date: 12/12/15 - 10:31 PM
 *
 * @author Jeroen Meulemeester
 */
public class MenuViewTest {

  @Test
  public void testStoreChanged() throws Exception {
    final MenuStore store = mock(MenuStore.class);
    when(store.getSelected()).thenReturn(MenuItem.HOME);

    final MenuView view = new MenuView();
    view.storeChanged(store);

    verify(store, times(1)).getSelected();
    verifyNoMoreInteractions(store);
  }

  @Test
  public void testItemClicked() throws Exception {
    final Store store = mock(Store.class);
    Dispatcher.getInstance().registerStore(store);

    final MenuView view = new MenuView();
    view.itemClicked(MenuItem.PRODUCTS);

    // We should receive a menu click action and a content changed action
    verify(store, times(2)).onAction(any(Action.class));

  }

}
