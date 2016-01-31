/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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
import com.iluwatar.featuretoggle.user.User;
import com.iluwatar.featuretoggle.user.UserGroup;

import java.util.Properties;

/**
 *
 */
public class App {

  /**
   *
   */
  public static void main(String[] args) {
    final Properties properties = new Properties();
    properties.put("enhancedWelcome", true);
    Service service = new PropertiesFeatureToggleVersion(properties);
    final String welcomeMessage = service.getWelcomeMessage(new User("Jamie No Code"));
    System.out.println(welcomeMessage);

    final Properties turnedOff = new Properties();
    turnedOff.put("enhancedWelcome", false);
    Service turnedOffService = new PropertiesFeatureToggleVersion(turnedOff);
    final String welcomeMessageturnedOff = turnedOffService.getWelcomeMessage(new User("Jamie No Code"));
    System.out.println(welcomeMessageturnedOff);

    final User paidUser = new User("Jamie Coder");
    final User freeUser = new User("Alan Defect");

    UserGroup.addUserToPaidGroup(paidUser);
    UserGroup.addUserToFreeGroup(freeUser);

    final String welcomeMessagePaidUser = service.getWelcomeMessage(paidUser);
    final String welcomeMessageFreeUser = service.getWelcomeMessage(freeUser);
    System.out.println(welcomeMessageFreeUser);
    System.out.println(welcomeMessagePaidUser);
  }
}
