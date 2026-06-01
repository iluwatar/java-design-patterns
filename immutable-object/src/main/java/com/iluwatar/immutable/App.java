package com.iluwatar.immutable;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * The Immutable Object pattern creates objects whose state cannot be changed after creation.
 * 
 * <p>This example demonstrates creating immutable UserProfile objects. Once created,
 * the user profile cannot be modified - any "change" creates a new object instead.</p>
 *
 * <p>Benefits of immutable objects:</p>
 * <ul>
 *   <li>Thread-safe without synchronization</li>
 *   <li>Can be shared freely between threads</li>
 *   <li>Make excellent map keys and set elements</li>
 *   <li>Fail-fast behavior - no invalid intermediate states</li>
 *   <li>Easier to reason about and test</li>
 * </ul>
 */
@Slf4j
public class App {

    /**
     * Program entry point.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        // Create initial preferences
        var preferences = new HashMap<String, String>();
        preferences.put("theme", "dark");
        preferences.put("language", "en");

        // Create an immutable user profile
        var profile = new UserProfile("john_doe", "john@example.com", 30, preferences);
        LOGGER.info("Created profile: {}", profile);

        // Demonstrate immutability - preferences map cannot be modified
        try {
            profile.getPreferences().put("newKey", "value");
        } catch (UnsupportedOperationException e) {
            LOGGER.info("Cannot modify preferences: {}", e.getMessage());
        }

        // Create a new profile with updated age (original remains unchanged)
        var updatedProfile = profile.withAge(31);
        LOGGER.info("Original profile: {}", profile);
        LOGGER.info("Updated profile: {}", updatedProfile);

        // Demonstrate that original map modification doesn't affect the profile
        preferences.put("fontSize", "14");
        LOGGER.info("Original preferences modified, but profile unaffected: {}", profile.getPreferences());
    }
}
