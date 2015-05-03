package com.iluwatar.view;

import com.iluwatar.action.MenuItem;
import com.iluwatar.dispatcher.Dispatcher;
import com.iluwatar.store.MenuStore;
import com.iluwatar.store.Store;

public class MenuView implements View {

	private MenuItem selected = MenuItem.HOME;
	
	@Override
	public void storeChanged(Store store) {
		MenuStore menuStore = (MenuStore) store;
		selected = menuStore.getSelected();
		render();
	}

	@Override
	public void render() {
		for (MenuItem item: MenuItem.values()) {
			if (selected.equals(item)) {
				System.out.println(String.format("* %s", item.toString()));
			} else {
				System.out.println(item.toString());
			}
		}
	}
	
	public void itemClicked(MenuItem item) {
		Dispatcher.getInstance().menuItemSelected(item);
	}
}
