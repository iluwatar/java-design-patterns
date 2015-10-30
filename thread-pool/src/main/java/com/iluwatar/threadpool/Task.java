package com.iluwatar.threadpool;

/**
 * 
 * Abstract base class for tasks
 *
 */
public abstract class Task {

	private static int nextId = 1;
	
	private final int id;
	private final int timeMs;
	
	public Task(final int timeMs) {
		this.id = nextId++;
		this.timeMs = timeMs;
	}
	
	public int getId() {
		return id;
	}
	
	public int getTimeMs() {
		return timeMs;
	}
	
	@Override
	public String toString() {
		return String.format("id=%d timeMs=%d", id, timeMs);
	}
}
