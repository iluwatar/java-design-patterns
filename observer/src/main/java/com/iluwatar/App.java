package com.iluwatar;

public class App 
{
    public static void main( String[] args )
    {

    	Weather weather = new Weather();
    	weather.addObserver(new Orcs());
    	weather.addObserver(new Hobbits());
    	
    	weather.timePasses();
    	weather.timePasses();
    	weather.timePasses();
    	weather.timePasses();
    	
    }
}
