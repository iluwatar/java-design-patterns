package com.iluwatar;

/**
 * 
 * Facade (DwarvenGoldmineFacade) provides simpler interface to 
 * subsystem.
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	DwarvenGoldmineFacade facade = new DwarvenGoldmineFacade();
    	facade.startNewDay();
    	facade.digOutGold();
    	facade.endDay();
    }
}
