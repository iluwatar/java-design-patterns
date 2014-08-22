package com.iluwatar;

import java.util.Stack;

public class App 
{
    public static void main( String[] args )
    {
    	Stack<StarMemento> states = new Stack<>();
    	
    	Star star = new Star(StarType.SUN, 10000000, 500000);
    	System.out.println(star);
    	states.add(star.getMemento());
    	star.timePasses();
    	System.out.println(star);
    	states.add(star.getMemento());
    	star.timePasses();
    	System.out.println(star);
    	states.add(star.getMemento());
    	star.timePasses();
    	System.out.println(star);
    	states.add(star.getMemento());
    	star.timePasses();
    	System.out.println(star);
    	while (states.size() > 0) {
    		star.setMemento(states.pop());
        	System.out.println(star);
    	}
    }
}
