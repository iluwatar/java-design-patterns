package com.iluwatar.immutable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Immutable Object pattern.
 *
 * <p>An immutable object is an object whose state cannot be modified after it is created. Immutable
 * objects are inherently thread-safe and can be shared freely between threads without
 * synchronization. This pattern is widely used in Java core libraries (e.g., {@code String}, {@code
 * Integer}, {@code LocalDate}).
 *
 * <p>Key characteristics of immutable objects:
 * <ul>
 *   <li>All fields are {@code final} and set in the constructor.</li>
 *   <li>No setter methods are provided.</li>
 *   <li>Mutable collections are wrapped with {@link Collections#unmodifiableMap} etc.</li>
 *   <li>The class is declared {@code final} to prevent subclassing.</li>
 * </ul>
 *
 * <p>This class demonstrates the pattern with an immutable user profile containing a name, email,
 * and an unmodifiable map of metadata.
 */
public final class UserInfo {

  private final String name;
  private final String email;
  private final Map<String, String> metadata;

  /**
   * Constructs an immutable UserInfo instance.
   *
   * @param name     the user's name
   * @param email    the user's email
   * @param metadata key-value metadata (defensively copied)
   */
  public UserInfo(String name, String email, Map<String, String> metadata) {
    this.name = name;
    this.email = email;
    // Defensive copy to ensure immutability
    this.metadata = Collections.unmodifiableMap(new HashMap<>(metadata));
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public Map<String, String> getMetadata() {
    return metadata;
  }

  /**
   * Returns a new UserInfo with the updated name, preserving other fields.
   *
   * @param newName the new name
   * @return a new UserInfo instance with the updated name
   */
  public UserInfo withName(String newName) {
    return new UserInfo(newName, this.email, this.metadata);
  }

  /**
   * Returns a new UserInfo with the updated email, preserving other fields.
   *
   * @param newEmail the new email
   * @return a new UserInfo instance with the updated email
   */
  public UserInfo withEmail(String newEmail) {
    return new UserInfo(this.name, newEmail, this.metadata);
  }

  /**
   * Returns a new UserInfo with an additional metadata entry.
   *
   * @param key   the metadata key
   * @param value the metadata value
   * @return a new UserInfo instance with the added metadata
   */
  public UserInfo withMetadata(String key, String value) {
    Map<String, String> newMetadata = new HashMap<>(this.metadata);
    newMetadata.put(key, value);
    return new UserInfo(this.name, this.email, newMetadata);
  }

  @Override
  public String toString() {
    return "UserInfo{name='" + name + "', email='" + email + "', metadata=" + metadata + "}";
  }
}
