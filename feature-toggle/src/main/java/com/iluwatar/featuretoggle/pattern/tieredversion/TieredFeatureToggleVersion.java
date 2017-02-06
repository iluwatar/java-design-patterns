/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.iluwatar.featuretoggle.pattern.tieredversion;

import com.iluwatar.featuretoggle.pattern.Service;
import com.iluwatar.featuretoggle.user.User;
import com.iluwatar.featuretoggle.user.UserGroup;

/**
 * This example of the Feature Toogle pattern shows how it could be implemented based on a {@link User}. Therefore
 * showing its use within a tiered application where the paying users get access to different content or
 * better versions of features. So in this instance a {@link User} is passed in and if they are found to be
 * on the {@link UserGroup#isPaid(User)} they are welcomed with a personalised message. While the other is more
 * generic. However this pattern is limited to simple examples such as the one below.
 *
 * @see Service
 * @see User
 * @see com.iluwatar.featuretoggle.pattern.propertiesversion.PropertiesFeatureToggleVersion
 * @see UserGroup
 */
public class TieredFeatureToggleVersion implements Service {

  /**
   * Generates a welcome message from the passed {@link User}. The resulting message depends on the group of the
   * {@link User}. So if the {@link User} is in the {@link UserGroup#paidGroup} then the enhanced version of the
   * welcome message will be returned where the username is displayed.
   *
   * @param user the {@link User} to generate the welcome message for, different messages are displayed if the user is
   *             in the {@link UserGroup#isPaid(User)} or {@link UserGroup#freeGroup}
   * @return Resulting welcome message.
   * @see User
   * @see UserGroup
   */
  @Override
  public String getWelcomeMessage(User user) {
    if (UserGroup.isPaid(user)) {
      return "You're amazing " + user + ". Thanks for paying for this awesome software.";
    }

    return "I suppose you can use this software.";
  }

  /**
   * Method that checks if the welcome message to be returned is the enhanced version. For this instance as the logic
   * is driven by the user group. This method is a little redundant. However can be used to show that there is an
   * enhanced version available.
   *
   * @return Boolean value {@value true} if enhanced.
   */
  @Override
  public boolean isEnhanced() {
    return true;
  }

}
