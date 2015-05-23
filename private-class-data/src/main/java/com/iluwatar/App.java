package com.iluwatar;

/**
 * 
 * The Private Class Data design pattern seeks to reduce exposure of attributes 
 * by limiting their visibility. It reduces the number of class attributes by 
 * encapsulating them in single data object. It allows the class designer to 
 * remove write privilege of attributes that are intended to be set only during 
 * construction, even from methods of the target class.
 * 
 * In the example we have normal Stew class with some ingredients given in
 * constructor. Then we have methods to enumerate the ingredients and to taste
 * the stew. The method for tasting the stew alters the private members of the
 * stew class.
 * 
 * The problem is solved with the Private Class Data pattern. We introduce
 * ImmutableStew class that contains StewData. The private data members of
 * Stew are now in StewData and cannot be altered by ImmutableStew methods.
 *
 */
public class App {
	
    public static void main( String[] args ) {
    	// stew is mutable
    	Stew stew = new Stew(1, 2, 3, 4);
    	stew.mix();
    	stew.taste();
    	stew.mix();
    	
    	// immutable stew protected with Private Class Data pattern
    	ImmutableStew immutableStew = new ImmutableStew(2, 4, 3, 6);
    	immutableStew.mix();
    }
}
