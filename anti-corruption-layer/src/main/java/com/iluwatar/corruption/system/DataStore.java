package com.iluwatar.corruption.system;

import java.util.HashMap;
import java.util.Optional;

public abstract class DataStore<V> {
    private final HashMap<String, V> inner;

    public DataStore() {
        inner = new HashMap<>();
    }

    public Optional<V> get(String key) {
        return Optional.ofNullable(inner.get(key));
    }

    public Optional<V> put(String key, V value) {
        return Optional.ofNullable(inner.put(key, value));

    }
}
