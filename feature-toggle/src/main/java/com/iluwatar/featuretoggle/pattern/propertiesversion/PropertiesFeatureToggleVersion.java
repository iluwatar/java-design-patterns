/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.featuretoggle.pattern.propertiesversion;

import com.iluwatar.featuretoggle.pattern.Service;
import com.iluwatar.featuretoggle.user.User;
import java.util.Properties;
import lombok.Getter;

/**
 * This example of the Feature Toogle pattern is less dynamic version than {@link
 * com.iluwatar.featuretoggle.pattern.tieredversion.TieredFeatureToggleVersion} where the feature is
 * turned on or off at the time of creation of the service. This example uses simple Java {@link
 * Properties} however it could as easily be done with an external configuration file loaded by
 * Spring and so on. A good example of when to use this version of the feature toggle is when new
 * features are being developed. So you could have a configuration property boolean named
 * development or some sort of system environment variable.
 *
 * @see Service
 * @see com.iluwatar.featuretoggle.pattern.tieredversion.TieredFeatureToggleVersion
 * @see User
 */
@Getter
public class PropertiesFeatureToggleVersion implements Service {

  /**
   * True if the welcome message to be returned is the enhanced venison or not. For
   * this service it will see the value of the boolean that was set in the constructor {@link
   * PropertiesFeatureToggleVersion#PropertiesFeatureToggleVersion(Properties)}
   */
  private final boolean enhanced;

  /**
   * Creates an instance of {@link PropertiesFeatureToggleVersion} using the passed {@link
   * Properties} to determine, the status of the feature toggle {@link
   * PropertiesFeatureToggleVersion#isEnhanced()}. There is also some defensive code to ensure the
   * {@link Properties} passed are as expected.
   *
   * @param properties {@link Properties} used to configure the service and toggle features.
   * @throws IllegalArgumentException when the passed {@link Properties} is not as expected
   * @see Properties
   */
  public PropertiesFeatureToggleVersion(final Properties properties) {
    if (properties == null) {
      throw new IllegalArgumentException("No Properties Provided.");
    } else {
      try {
        enhanced = (boolean) properties.get("enhancedWelcome");
      } catch (Exception e) {
        throw new IllegalArgumentException("Invalid Enhancement Settings Provided.");
      }
    }
  }

  /**
   * Generate a welcome message based on the user being passed and the status of the feature toggle.
   * If the enhanced version is enabled, then the message will be personalised with the name of the
   * passed {@link User}. However if disabled then a generic version fo the message is returned.
   *
   * @param user the {@link User} to be displayed in the message if the enhanced version is enabled
   *             see {@link PropertiesFeatureToggleVersion#isEnhanced()}. If the enhanced version is
   *             enabled, then the message will be personalised with the name of the passed {@link
   *             User}. However if disabled then a generic version fo the message is returned.
   * @return Resulting welcome message.
   * @see User
   */
  @Override
  public String getWelcomeMessage(final User user) {

    if (isEnhanced()) {
      return "Welcome " + user + ". You're using the enhanced welcome message.";
    }

    return "Welcome to the application.";
  }
}
