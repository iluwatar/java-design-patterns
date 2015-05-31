package com.iluwatar.resourceacquisitionisinitialization;

import java.io.Closeable;
import java.io.IOException;

/**
 * 
 * TreasureChest resource
 *
 */
public class TreasureChest implements Closeable {

	public TreasureChest() {
		System.out.println("Treasure chest opens.");
	}
	
	@Override
	public void close() throws IOException {
		System.out.println("Treasure chest closes.");
	}
}
