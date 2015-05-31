package com.iluwatar.flux.store;

import com.iluwatar.flux.action.Action;
import com.iluwatar.flux.action.ActionType;
import com.iluwatar.flux.action.Content;
import com.iluwatar.flux.action.ContentAction;

/**
 * 
 * ContentStore is a concrete store.
 *
 */
public class ContentStore extends Store {

	private Content content = Content.PRODUCTS;

	@Override
	public void onAction(Action action) {
		if (action.getType().equals(ActionType.CONTENT_CHANGED)) {
			ContentAction contentAction = (ContentAction) action;
			content = contentAction.getContent();
			notifyChange();
		}
	}
	
	public Content getContent() {
		return content;
	}
}
