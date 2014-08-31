package com.iluwatar;

/**
 * 
 * In State pattern the object (Mammoth) has internal
 * state object (State) that defines the current
 * behavior. The state object can be changed
 * to alter the behavior.
 *
 */
public class App 
{
    public static void main( String[] args )
    {

    	Mammoth mammoth = new Mammoth();
    	mammoth.observe();
    	mammoth.timePasses();
    	mammoth.observe();
    	mammoth.timePasses();
    	mammoth.observe();
    	
    }
}
