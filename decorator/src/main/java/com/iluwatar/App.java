package com.iluwatar;

/**
 * 
 * Decorator pattern is more flexible alternative to
 * subclassing. The decorator class implements the same
 * interface as the target and uses composition to
 * "decorate" calls to the target.
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	
    	System.out.println("A simple looking troll approaches.");
    	Hostile troll = new Troll();
    	troll.attack();
    	troll.fleeBattle();
    	
    	System.out.println("\nA smart looking troll surprises you.");
    	Hostile smart = new SmartTroll(new Troll());
    	smart.attack();
    	smart.fleeBattle();
    }
}
