package com.iluwatar.view;

import com.iluwatar.store.Store;

public interface View {

	public void storeChanged(Store store);

	public void render();
}
