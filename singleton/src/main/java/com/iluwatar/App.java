package com.iluwatar;

public class App 
{
    public static void main( String[] args )
    {
    	
    	IvoryTower ivoryTower1 = IvoryTower.getInstance();
    	IvoryTower ivoryTower2 = IvoryTower.getInstance();
    	System.out.println("ivoryTower1=" + ivoryTower1);
    	System.out.println("ivoryTower2=" + ivoryTower2);
    	
    }
}
