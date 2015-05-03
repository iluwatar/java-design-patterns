package com.iluwatar.store;

import com.iluwatar.action.Action;
import com.iluwatar.action.ActionType;
import com.iluwatar.action.Content;
import com.iluwatar.action.ContentAction;

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
