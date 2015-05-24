package com.iluwatar;

import java.util.HashSet;

public abstract class ObjectPool<T> {

	HashSet<T> available = new HashSet<>();
	HashSet<T> inUse = new HashSet<>();
	
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
