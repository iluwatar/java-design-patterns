package com.iluwatar;

public class App 
{
    public static void main( String[] args )
    {
    	
    	System.out.println("A simple looking troll approaches.");
    	Troll troll = new Troll();
    	troll.attack();
    	troll.fleeBattle();
    	
    	System.out.println("\nA smart looking troll surprises you.");
    	Troll smart = new SmartTroll(new Troll());
    	smart.attack();
    	smart.fleeBattle();
    }
}
