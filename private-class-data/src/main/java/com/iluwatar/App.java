package com.iluwatar;

public class App {
	
    public static void main( String[] args ) {
    	Stew stew = new Stew(1, 2, 3, 4);
    	stew.mix();
    	stew.taste();
    	stew.mix();
    	
    	ImmutableStew immutableStew = new ImmutableStew(2, 4, 3, 6);
    	immutableStew.mix();
    }
}
