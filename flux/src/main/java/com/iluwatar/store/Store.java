package com.iluwatar.store;

import java.util.LinkedList;
import java.util.List;

import com.iluwatar.action.Action;
import com.iluwatar.view.View;

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
