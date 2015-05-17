package com.iluwatar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
	
    public static void main( String[] args ) {
    	
    	System.out.println("Program started");
    	
    	List<Task> tasks = new ArrayList<>();
    	tasks.add(new PotatoPeelingTask(3));
    	tasks.add(new PotatoPeelingTask(6));
    	tasks.add(new CoffeeMakingTask(2));
    	tasks.add(new CoffeeMakingTask(6));
    	tasks.add(new PotatoPeelingTask(4));
    	tasks.add(new CoffeeMakingTask(2));
    	tasks.add(new PotatoPeelingTask(4));
    	tasks.add(new CoffeeMakingTask(9));
    	tasks.add(new PotatoPeelingTask(3));
    	tasks.add(new CoffeeMakingTask(2));
    	tasks.add(new PotatoPeelingTask(4));
    	tasks.add(new CoffeeMakingTask(2));
    	tasks.add(new CoffeeMakingTask(7));
    	tasks.add(new PotatoPeelingTask(4));
    	tasks.add(new PotatoPeelingTask(5));
    	
    	ExecutorService executor = Executors.newFixedThreadPool(3);
    	for (int i=0; i<tasks.size(); i++) {
	    	Runnable worker = new Worker(tasks.get(i));
	    	executor.execute(worker);
    	}
    	executor.shutdown();
    	while (!executor.isTerminated()) {
    	}
    	System.out.println("Program finished");
    }
}
