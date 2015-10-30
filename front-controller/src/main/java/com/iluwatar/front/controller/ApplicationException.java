package com.iluwatar.front.controller;

/**
 * 
 * Custom exception type
 *
 */
public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ApplicationException(Throwable cause) {
        super(cause);
    }
}
