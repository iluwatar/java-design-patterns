package com.iluwatar.abstractdocument;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public interface Document {

	/**
	 * Puts the value related to the key
	 * 
	 * @param key
	 * @param value
	 * @return Void
	 */
	Void put(String key, Object value);

	/**
	 * Gets the value for the key
	 * 
	 * @param key
	 * @return value or null
	 */
	Object get(String key);

	/**
	 * Gets the stream of child documents
	 * 
	 * @param key
	 * @param constructor
	 * @return child documents
	 */
	<T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor);
}
