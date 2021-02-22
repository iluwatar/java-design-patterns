package com.iluwatar.activeobject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import java.util.logging.Level;

/**
 * 
 * @author Noam Greenshtain
 * 
 * The Active Object pattern helps to solve synchronization difficulties without using 'synchronized' methods.
 * The active object will contain a thread-safe data structure(such as BlockingQueue) and use to synchronize
 * method calls by moving the logic of the method into an invocator(usually a Runnable) and store it in the DSA.
 *
 * In this example, we fire 10 threads to modify a value in the target class.
 *
 */
public class App {
	
	public final static int WORKERS = 20;
	
	private final static Logger LOGGER = Logger.getLogger(ActiveCounter.class.getName());

	public static void main(String[] args) {	
		ActiveCounter counter = new ActiveCounter();
		ExecutorService e = Executors.newCachedThreadPool();
		for(int i=0;i<WORKERS;i++) {
			e.execute(new Runnable() {			
				@Override
				public void run() {
					try {
						counter.incremenet();
						counter.printVal();
					} catch (InterruptedException e) {
						LOGGER.log(Level.SEVERE, e.getMessage());
					}
				}
				
			});
		}
		try {
			e.awaitTermination(1, TimeUnit.SECONDS);
		} catch (InterruptedException e1) {
			LOGGER.log(Level.SEVERE, e1.getMessage());
		}
		System.exit(1);
	}

}
