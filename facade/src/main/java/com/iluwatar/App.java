package com.iluwatar;

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
