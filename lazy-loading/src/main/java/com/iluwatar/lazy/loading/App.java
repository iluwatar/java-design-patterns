package com.iluwatar.lazy.loading;

/**
 * 
 * Lazy loading idiom defers object creation until needed.
 * <p>
 * This example shows different implementations of the pattern
 * with increasing sophistication.
 * <p>
 * Additional information and lazy loading flavours are described in
 * http://martinfowler.com/eaaCatalog/lazyLoad.html
 *
 */
public class App 
{
	/**
	 * Program entry point
	 * @param args command line args
	 */
    public static void main( String[] args ) {
    	
    	// Simple lazy loader - not thread safe
    	HolderNaive holderNaive = new HolderNaive();
    	Heavy heavy = holderNaive.getHeavy();
    	System.out.println("heavy=" + heavy);
    	
    	// Thread safe lazy loader, but with heavy synchronization on each access
    	HolderThreadSafe holderThreadSafe = new HolderThreadSafe();
    	Heavy another = holderThreadSafe.getHeavy();
    	System.out.println("another=" + another);
    	
    	// The most efficient lazy loader utilizing Java 8 features
    	Java8Holder java8Holder = new Java8Holder();
    	Heavy next = java8Holder.getHeavy();
    	System.out.println("next=" + next);
    }
}
