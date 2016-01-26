
package com.iluwatar.featuretoggle.pattern.propertiesversion;

import com.iluwatar.featuretoggle.pattern.Service;
import com.iluwatar.featuretoggle.user.User;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PropertiesFeatureToggleVersionTest {

  @Test(expected = IllegalArgumentException.class)
  public void testNullPropertiesPassed() throws Exception {
    new PropertiesFeatureToggleVersion(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNonBooleanProperty() throws Exception {
    final Properties properties = new Properties();
    properties.setProperty("enhancedWelcome","Something");
    new PropertiesFeatureToggleVersion(properties);
  }

  @Test
  public void testFeatureTurnedOn() throws Exception {
    final Properties properties = new Properties();
    properties.put("enhancedWelcome",true);
    Service service = new PropertiesFeatureToggleVersion(properties);
    assertTrue(service.isEnhanced());
    final String welcomeMessage = service.getWelcomeMessage(new User("Jamie No Code"));
    assertEquals("Welcome Jamie No Code. You're using the enhanced welcome message.",welcomeMessage);
  }

  @Test
  public void testFeatureTurnedOff() throws Exception {
    final Properties properties = new Properties();
    properties.put("enhancedWelcome",false);
    Service service = new PropertiesFeatureToggleVersion(properties);
    assertFalse(service.isEnhanced());
    final String welcomeMessage = service.getWelcomeMessage(new User("Jamie No Code"));
    assertEquals("Welcome to the application.",welcomeMessage);
  }
}