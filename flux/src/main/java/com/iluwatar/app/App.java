package com.iluwatar.app;

import com.iluwatar.action.MenuItem;
import com.iluwatar.dispatcher.Dispatcher;
import com.iluwatar.store.ContentStore;
import com.iluwatar.store.MenuStore;
import com.iluwatar.view.ContentView;
import com.iluwatar.view.MenuView;

public class App {
	
    public static void main( String[] args ) {
    	// initialize
    	MenuStore menuStore = new MenuStore();
    	Dispatcher.getInstance().registerStore(menuStore);
    	ContentStore contentStore = new ContentStore();
    	Dispatcher.getInstance().registerStore(contentStore);
    	MenuView menuView = new MenuView();
    	menuStore.registerView(menuView);
    	ContentView contentView = new ContentView();
    	contentStore.registerView(contentView);
    	
    	// render initial view
    	menuView.render();
    	contentView.render();
    	
    	// user clicks another menu item
    	// this triggers action dispatching and eventually causes views to render with new content
    	menuView.itemClicked(MenuItem.COMPANY);
    }
}
