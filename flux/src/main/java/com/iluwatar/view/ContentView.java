package com.iluwatar.view;

import com.iluwatar.action.Content;
import com.iluwatar.store.ContentStore;
import com.iluwatar.store.Store;

/**
 * 
 * ContentView is a concrete view.
 *
 */
public class ContentView implements View {

	private Content content = Content.PRODUCTS;

	@Override
	public void storeChanged(Store store) {
		ContentStore contentStore = (ContentStore) store;
		content = contentStore.getContent();
		render();
	}

	@Override
	public void render() {
		System.out.println(content.toString());
	}
}
