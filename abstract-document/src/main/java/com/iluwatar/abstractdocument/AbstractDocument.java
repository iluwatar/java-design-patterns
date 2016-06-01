package com.iluwatar.abstractdocument;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class AbstractDocument implements Document {

	private final Map<String, Object> properties;

	protected AbstractDocument(Map<String, Object> properties) {
		Objects.requireNonNull(properties, "properties map is required");
		this.properties = properties;
	}

	@Override
	public Void put(String key, Object value) {
		properties.put(key, value);
		return null;
	}

	@Override
	public Object get(String key) {
		return properties.get(key);
	}

	@Override
	public <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor) {
		return Stream.of(get(key))
				.filter(el -> el != null)
				.map(el -> (List<Map<String, Object>>) el)
				.findFirst().get().stream()
				.map(constructor);
	}

}
