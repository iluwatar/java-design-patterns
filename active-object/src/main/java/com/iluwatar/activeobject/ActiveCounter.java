package com.iluwatar.activeobject;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveCounter {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ActiveCounter.class.getName());
    private Integer val;
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
                    LOGGER.info("val has been set to 0.");
                }
            }
        );
    }

    public void incremenet() throws InterruptedException {
    	requests.put(new Runnable() {
                @Override
                public void run() { 
                    val++; 
                    LOGGER.info("val has been incremented.");
                }
            }
        );
    }
    
    public void printVal() throws InterruptedException {
    	requests.put(new Runnable() {
                @Override
                public void run() { 
                	LOGGER.info(val.toString());
                }
            }
        );
    }
   
    
}
