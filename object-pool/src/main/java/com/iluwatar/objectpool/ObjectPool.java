package com.iluwatar.objectpool;

import java.util.HashSet;

/**
 * 
 * Generic object pool
 *
 * @param <T>
 */
public abstract class ObjectPool<T> {

	private HashSet<T> available = new HashSet<>();
	private HashSet<T> inUse = new HashSet<>();
	
	protected abstract T create();
	
	public synchronized T checkOut() {
		if (available.size() <= 0) {
			available.add(create());
		}
		T instance = available.iterator().next();
		available.remove(instance);
		inUse.add(instance);
		return instance;
	}
	
	public synchronized void checkIn(T instance) {
		inUse.remove(instance);
		available.add(instance);
	}
	
	@Override
	public String toString() {
		return String.format("Pool available=%d inUse=%d", available.size(), inUse.size());
	}
}
