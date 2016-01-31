package com.iluwatar.featuretoggle.pattern.tieredversion;

import com.iluwatar.featuretoggle.pattern.Service;
import com.iluwatar.featuretoggle.user.User;
import com.iluwatar.featuretoggle.user.UserGroup;

/**
 *
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
