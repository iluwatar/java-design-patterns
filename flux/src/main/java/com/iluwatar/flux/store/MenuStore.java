package com.iluwatar.flux.store;

import com.iluwatar.flux.action.Action;
import com.iluwatar.flux.action.ActionType;
import com.iluwatar.flux.action.MenuAction;
import com.iluwatar.flux.action.MenuItem;

/**
 * 
 * MenuStore is a concrete store.
 *
 */
public class MenuStore extends Store {

	private MenuItem selected = MenuItem.HOME;
	
	@Override
	public void onAction(Action action) {
		if (action.getType().equals(ActionType.MENU_ITEM_SELECTED)) {
			MenuAction menuAction = (MenuAction) action;
			selected = menuAction.getMenuItem();
			notifyChange();
		}
	}
	
	public MenuItem getSelected() {
		return selected;
	}
}
