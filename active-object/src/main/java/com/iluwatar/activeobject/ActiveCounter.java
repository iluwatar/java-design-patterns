package com.iluwatar.activeobject;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ActiveCounter {
	
    private int val;
    private BlockingQueue<Runnable> requests;

    public ActiveCounter() {
    	this.requests = new LinkedBlockingQueue<Runnable>();
    	this.val = 0;
        new Thread (new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                        	requests.take().run();
                        } catch (InterruptedException e) {   
                            
                        }
                    }
                }
            }
        ).start();
    }

    
    public void zerorize() throws InterruptedException {
    	requests.put(new Runnable() {
                @Override
                public void run() { 
                    val = 0; 
                    System.out.println("val has been set to 0.");
                }
            }
        );
    }

    public void incremenet() throws InterruptedException {
    	requests.put(new Runnable() {
                @Override
                public void run() { 
                    val++; 
                    System.out.println("val has been incremented.");
                }
            }
        );
    }
    
    public void printVal() throws InterruptedException {
    	requests.put(new Runnable() {
                @Override
                public void run() { 
                    System.out.println(val);
                }
            }
        );
    }
   
    
}
