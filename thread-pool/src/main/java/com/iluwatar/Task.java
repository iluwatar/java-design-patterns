package com.iluwatar;

public abstract class Task {

	private static int nextId = 1;
	
	private int id;
	private int timeMs;
	
	public Task(int timeMs) {
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
