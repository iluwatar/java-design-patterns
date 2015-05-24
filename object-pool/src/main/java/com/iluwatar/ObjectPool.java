package com.iluwatar;

public abstract class ObjectPool<T> {

	protected abstract T create();
	
	public synchronized T checkOut() {
		return null;
	}
	
	public synchronized void checkIn(T instance) {
		
	}
}
