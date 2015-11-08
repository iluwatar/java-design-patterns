package com.iluwatar.flux.store;

import java.util.LinkedList;
import java.util.List;

import com.iluwatar.flux.action.Action;
import com.iluwatar.flux.view.View;

/**
 * 
 * Store is a data model.
 *
 */
public abstract class Store {
	
	private List<View> views = new LinkedList<>();
	
	public abstract void onAction(Action action);

	public void registerView(View view) {
		views.add(view);
	}
	
	protected void notifyChange() {
		views.stream().forEach((view) -> view.storeChanged(this));
	}
}
