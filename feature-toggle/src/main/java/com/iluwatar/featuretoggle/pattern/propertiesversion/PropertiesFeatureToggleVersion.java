package com.iluwatar.featuretoggle.pattern.propertiesversion;

import com.iluwatar.featuretoggle.pattern.Service;
import com.iluwatar.featuretoggle.user.User;

import java.util.Properties;

public class PropertiesFeatureToggleVersion implements Service {

  private Properties properties;

  public PropertiesFeatureToggleVersion(final Properties properties) {
    this.properties = properties;
  }

  @Override
  public String getWelcomeMessage(final User user) {

    final boolean enhancedWelcome = (boolean) properties.get("enhancedWelcome");

    if (enhancedWelcome) {
      return "Welcome " + user + ". You're using the enhanced welcome message.";
    }

    return "Welcome to the application.";
  }
}
