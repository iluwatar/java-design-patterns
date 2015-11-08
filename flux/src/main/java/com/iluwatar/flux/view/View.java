package com.iluwatar.flux.view;

import com.iluwatar.flux.store.Store;

/**
 * 
 * Views define the representation of data.
 *
 */
public interface View {

	public void storeChanged(Store store);

	public void render();
}
