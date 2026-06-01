package com.iluwatar.immutable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Tests for the Immutable Object pattern using UserProfile.
 */
class UserProfileTest {

    @Test
    void testImmutableProfileCreation() {
        var preferences = Map.of("theme", "dark");
        var profile = new UserProfile("test_user", "test@example.com", 25, preferences);

        assertEquals("test_user", profile.getUsername());
        assertEquals("test@example.com", profile.getEmail());
        assertEquals(25, profile.getAge());
        assertEquals("dark", profile.getPreferences().get("theme"));
    }

    @Test
    void testPreferencesAreImmutable() {
        var preferences = new HashMap<String, String>();
        preferences.put("theme", "dark");

        var profile = new UserProfile("test_user", "test@example.com", 25, preferences);

        // Should throw UnsupportedOperationException
        assertThrows(UnsupportedOperationException.class,
                () -> profile.getPreferences().put("newKey", "value"));
    }

    @Test
    void testConstructorDefensivelyCopiesPreferences() {
        var preferences = new HashMap<String, String>();
        preferences.put("theme", "dark");

        var profile = new UserProfile("test_user", "test@example.com", 25, preferences);

        // Modify original map
        preferences.put("theme", "light");

        // Profile should retain original value
        assertEquals("dark", profile.getPreferences().get("theme"));
    }

    @Test
    void testWithAgeReturnsNewInstance() {
        var profile = new UserProfile("test_user", "test@example.com", 25, Map.of());
        var updatedProfile = profile.withAge(30);

        assertEquals(25, profile.getAge());
        assertEquals(30, updatedProfile.getAge());
        assertNotEquals(profile, updatedProfile);
    }
}
