package com.iluwatar;

/**
 * 
 * Flyweight (PotionFactory) is useful when there is plethora of
 * objects (Potion). It provides means to decrease resource usage
 * by sharing object instances.
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	AlchemistShop alchemistShop = new AlchemistShop();
    	alchemistShop.enumerate();
    }
}
