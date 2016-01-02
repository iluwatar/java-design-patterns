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
public class Dispatcher {

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
    stores.stream().forEach((store) -> store.onAction(action));
  }
}
