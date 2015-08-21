package com.iluwatar.callback;

/**
 * 
 * Template-method class for callback hook execution
 * 
 */
public abstract class Task {

	public final void executeWith(Callback callback) {
		execute();
		if (callback != null) {
			callback.call();
		}
	}

	public abstract void execute();
}
