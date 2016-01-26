package com.iluwatar.featuretoggle.pattern.propertiesversion;

import com.iluwatar.featuretoggle.pattern.Service;
import com.iluwatar.featuretoggle.user.User;

import java.util.Properties;

public class PropertiesFeatureToggleVersion implements Service {

  private boolean isEnhanced;

  /**
   *
   * @param properties {@link Properties} used to configure the service and toggle features.
   */
  public PropertiesFeatureToggleVersion(final Properties properties) {
    if (properties == null) {
      throw new IllegalArgumentException("No Properties Provided.");
    } else {
      try {
        isEnhanced = (boolean) properties.get("enhancedWelcome");
      } catch (Exception e) {
        throw new IllegalArgumentException("Invalid Enhancement Settings Provided.");
      }
    }
  }

  @Override
  public String getWelcomeMessage(final User user) {

    if (isEnhanced()) {
      return "Welcome " + user + ". You're using the enhanced welcome message.";
    }

    return "Welcome to the application.";
  }

  @Override
  public boolean isEnhanced() {
    return isEnhanced;
  }
}
