package com.iluwatar.immutable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * An immutable user profile that cannot be changed after creation.
 * 
 * <p>The Immutable Object pattern ensures that an object's state cannot be modified
 * after it is constructed. This provides thread safety without synchronization,
 * makes code easier to reason about, and prevents accidental state mutation.</p>
 *
 * <p>Key characteristics of immutable objects:</p>
 * <ul>
 *   <li>The class is declared {@code final} to prevent subclassing</li>
 *   <li>All fields are {@code private} and {@code final}</li>
 *   <li>No setter methods are provided</li>
 *   <li>Mutable fields are defensively copied in constructor and getters</li>
 *   <li>The class cannot be extended (final class)</li>
 * </ul>
 */
public final class UserProfile {

    private final String username;
    private final String email;
    private final int age;
    private final Map<String, String> preferences;

    /**
     * Constructs an immutable UserProfile.
     *
     * @param username    the username
     * @param email       the email address
     * @param age         the age
     * @param preferences the user preferences (defensively copied)
     */
    public UserProfile(String username, String email, int age, Map<String, String> preferences) {
        this.username = username;
        this.email = email;
        this.age = age;
        // Defensive copy - store a copy of the mutable map
        this.preferences = Collections.unmodifiableMap(new HashMap<>(preferences));
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    /**
     * Returns an unmodifiable view of the preferences.
     *
     * @return an unmodifiable map of preferences
     */
    public Map<String, String> getPreferences() {
        return preferences;
    }

    /**
     * Creates a new UserProfile with updated age.
     * Since the object is immutable, a new instance is returned.
     *
     * @param newAge the new age
     * @return a new UserProfile with the updated age
     */
    public UserProfile withAge(int newAge) {
        return new UserProfile(this.username, this.email, newAge, this.preferences);
    }

    @Override
    public String toString() {
        return String.format("UserProfile{username='%s', email='%s', age=%d, preferences=%s}",
                username, email, age, preferences);
    }
}
