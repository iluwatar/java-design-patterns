package com.iluwatar.featuretoggle.pattern.tieredversion;

import com.iluwatar.featuretoggle.pattern.Service;
import com.iluwatar.featuretoggle.user.User;
import com.iluwatar.featuretoggle.user.UserGroup;

public class TieredFeatureToggleVersion implements Service {

  @Override
  public String getWelcomeMessage(User user) {
    if (UserGroup.isPaid(user)) {
      return "You're amazing " + user.getName() + ". Thanks for paying for this awesome software.";
    }

    return "I suppose you can use this software.";
  }

}
