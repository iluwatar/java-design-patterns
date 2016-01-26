
package com.iluwatar.featuretoggle.pattern.propertiesversion;

import com.iluwatar.featuretoggle.pattern.Service;
import com.iluwatar.featuretoggle.user.User;
import org.junit.Test;

import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class PropertiesFeatureToggleVersionTest {

  @Test
  public void testFeatureTurnedOn() throws Exception {
    final Properties properties = new Properties();
    properties.put("enhancedWelcome",true);
    Service service = new PropertiesFeatureToggleVersion(properties);
    final String welcomeMessage = service.getWelcomeMessage(new User("Jamie No Code"));
    assertEquals("Welcome Jamie No Code. You're using the enhanced welcome message.",welcomeMessage);
  }

  @Test
  public void testFeatureTurnedOff() throws Exception {
    final Properties properties = new Properties();
    properties.put("enhancedWelcome",false);
    Service service = new PropertiesFeatureToggleVersion(properties);
    final String welcomeMessage = service.getWelcomeMessage(new User("Jamie No Code"));
    assertEquals("Welcome to the application.",welcomeMessage);
  }
}