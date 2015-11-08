package com.iluwatar.lazy.loading;

/**
 * 
 * Simple implementation of the lazy loading idiom.
 * However, this is not thread safe.
 *
 */
public class HolderNaive {
	
	private Heavy heavy;
	
	public HolderNaive() {
		System.out.println("HolderNaive created");
	}

	public Heavy getHeavy() {
		if (heavy == null) {
			heavy = new Heavy();
		}
		return heavy;
	}
}
