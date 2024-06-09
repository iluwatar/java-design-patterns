/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
package com.iluwatar.featuretoggle;

import com.iluwatar.featuretoggle.pattern.Service;
import com.iluwatar.featuretoggle.pattern.propertiesversion.PropertiesFeatureToggleVersion;
import com.iluwatar.featuretoggle.pattern.tieredversion.TieredFeatureToggleVersion;
import com.iluwatar.featuretoggle.user.User;
import com.iluwatar.featuretoggle.user.UserGroup;
import java.util.Properties;
import lombok.extern.slf4j.Slf4j;

/**
 * The Feature Toggle pattern allows for complete code executions to be turned on or off with ease.
 * This allows features to be controlled by either dynamic methods just as {@link User} information
 * or by {@link Properties}. In the App below there are two examples. Firstly the {@link Properties}
 * version of the feature toggle, where the enhanced version of the welcome message which is
 * personalised is turned either on or off at instance creation. This method is not as dynamic as
 * the {@link User} driven version where the feature of the personalised welcome message is
 * dependent on the {@link UserGroup} the {@link User} is in. So if the user is a member of the
 * {@link UserGroup#isPaid(User)} then they get an enhanced version of the welcome message.
 *
 * <p>Note that this pattern can easily introduce code complexity, and if not kept in check can
 * result in redundant unmaintained code within the codebase.
 */
@Slf4j
public class App {

  /**
   * Block 1 shows the {@link PropertiesFeatureToggleVersion} being run with {@link Properties}
   * setting the feature toggle to enabled.
   *
   * <p>Block 2 shows the {@link PropertiesFeatureToggleVersion} being run with {@link Properties}
   * setting the feature toggle to disabled. Notice the difference with the printed welcome message
   * the username is not included.
   *
   * <p>Block 3 shows the {@link
   * com.iluwatar.featuretoggle.pattern.tieredversion.TieredFeatureToggleVersion} being set up with
   * two users on who is on the free level, while the other is on the paid level. When the {@link
   * Service#getWelcomeMessage(User)} is called with the paid {@link User} note that the welcome
   * message contains their username, while the same service call with the free tier user is more
   * generic. No username is printed.
   *
   * @see User
   * @see UserGroup
   * @see Service
   * @see PropertiesFeatureToggleVersion
   * @see com.iluwatar.featuretoggle.pattern.tieredversion.TieredFeatureToggleVersion
   */
  public static void main(String[] args) {

    // Demonstrates the PropertiesFeatureToggleVersion running with properties
    // that set the feature toggle to enabled.

    final var properties = new Properties();
    properties.put("enhancedWelcome", true);
    var service = new PropertiesFeatureToggleVersion(properties);
    final var welcomeMessage = service.getWelcomeMessage(new User("Jamie No Code"));
    LOGGER.info(welcomeMessage);

    // Demonstrates the PropertiesFeatureToggleVersion running with properties
    // that set the feature toggle to disabled. Note the difference in the printed welcome message
    // where the username is not included.

    final var turnedOff = new Properties();
    turnedOff.put("enhancedWelcome", false);
    var turnedOffService = new PropertiesFeatureToggleVersion(turnedOff);
    final var welcomeMessageturnedOff =
        turnedOffService.getWelcomeMessage(new User("Jamie No Code"));
    LOGGER.info(welcomeMessageturnedOff);

    // Demonstrates the TieredFeatureToggleVersion setup with
    // two users: one on the free tier and the other on the paid tier. When the
    // Service#getWelcomeMessage(User) method is called with the paid user, the welcome
    // message includes their username. In contrast, calling the same service with the free tier user results
    // in a more generic welcome message without the username.

    var service2 = new TieredFeatureToggleVersion();

    final var paidUser = new User("Jamie Coder");
    final var freeUser = new User("Alan Defect");

    UserGroup.addUserToPaidGroup(paidUser);
    UserGroup.addUserToFreeGroup(freeUser);

    final var welcomeMessagePaidUser = service2.getWelcomeMessage(paidUser);
    final var welcomeMessageFreeUser = service2.getWelcomeMessage(freeUser);
    LOGGER.info(welcomeMessageFreeUser);
    LOGGER.info(welcomeMessagePaidUser);
  }
}
