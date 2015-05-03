package com.iluwatar.view;

import com.iluwatar.store.Store;

/**
 * 
 * Views define the representation of data.
 *
 */
public interface View {

	public void storeChanged(Store store);

	public void render();
}
