package com.iluwatar.doublechecked.locking;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 
 * Inventory
 *
 */
public class Inventory {

	private int inventorySize;
	private List<Item> items;
	private Lock lock = new ReentrantLock();

	public Inventory(int inventorySize) {
		this.inventorySize = inventorySize;
		this.items = new ArrayList<Item>(inventorySize);
	}

	public boolean addItem(Item item) {
		if (items.size() < inventorySize) {
			lock.lock();
			try {
				if (items.size() < inventorySize) {
					items.add(item);
					System.out.println(Thread.currentThread());
					return true;
				}
			} finally {
				lock.unlock();
			}
		}
		return false;
	}

}
