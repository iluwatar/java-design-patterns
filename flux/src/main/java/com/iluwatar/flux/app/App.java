package com.iluwatar.flux.app;

import com.iluwatar.flux.action.MenuItem;
import com.iluwatar.flux.dispatcher.Dispatcher;
import com.iluwatar.flux.store.ContentStore;
import com.iluwatar.flux.store.MenuStore;
import com.iluwatar.flux.view.ContentView;
import com.iluwatar.flux.view.MenuView;

/**
 * 
 * Flux is the application architecture that Facebook uses for building client-side web 
 * applications. Flux eschews MVC in favor of a unidirectional data flow. When a user interacts with 
 * a React view, the view propagates an action through a central dispatcher, to the various stores that 
 * hold the application's data and business logic, which updates all of the views that are affected.  
 * <p>
 * This example has two views: menu and content. They represent typical main menu and content area of
 * a web page. When menu item is clicked it triggers events through the dispatcher. The events are
 * received and handled by the stores updating their data as needed. The stores then notify the views
 * that they should rerender themselves.
 * <p>
 * http://facebook.github.io/flux/docs/overview.html
 *
 */
public class App {
	
	/**
	 * Program entry point
	 * @param args command line args
	 */
    public static void main( String[] args ) {
    	
    	// initialize and wire the system
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
