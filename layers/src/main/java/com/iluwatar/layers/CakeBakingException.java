package com.iluwatar.layers;

/**
 * 
 * Custom exception used in cake baking
 *
 */
public class CakeBakingException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public CakeBakingException() {
	}

	public CakeBakingException(String message) {
		super(message);
	}
}
