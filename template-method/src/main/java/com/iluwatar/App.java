package com.iluwatar;

/**
 * 
 * Template Method (StealingMethod) defines skeleton for the
 * algorithm and subclasses (HitAndRunMethod, SubtleMethod) 
 * fill in the blanks.
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	HalflingThief thief = new HalflingThief(new HitAndRunMethod());
    	thief.steal();
    	thief.changeMethod(new SubtleMethod());
    	thief.steal();
    }
}
