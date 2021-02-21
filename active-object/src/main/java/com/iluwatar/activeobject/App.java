package com.iluwatar.activeobject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author Noam Greenshtain
 * 
 * The Active Object pattern helps to solve synchronization difficulities without using 'syncrhonized' methods.
 * The active object will contain a thread-safe data structure(such as BlockingQueue) and use to synchronize
 * method calls by moving the logic of the method into an invocator(usuallya Runnable) and store it in the DSA.
 *
 * In this example, we fire 10 threads to modify a value in the target class.
 *
 */
public class App {
	
	public final static int WORKERS = 20;

	public static void main(String[] args) {	
		ActiveCounter counter = new ActiveCounter();
		ExecutorService e = Executors.newCachedThreadPool();
		for(int i=0;i<WORKERS;i++) {
			int j = i;
			e.execute(new Runnable() {
					
				@Override
				public void run() {
					try {
						counter.incremenet();
						counter.printVal();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			});
		}
	}

}
