package com.iluwatar.featuretoggle.pattern.tieredversion;

import com.iluwatar.featuretoggle.pattern.Service;
import com.iluwatar.featuretoggle.user.User;
import com.iluwatar.featuretoggle.user.UserGroup;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TieredFeatureToggleVersionTest {

    final User paidUser = new User("Jamie Coder");
    final User freeUser = new User("Alan Defect");
    final Service service = new TieredFeatureToggleVersion();

    @Before
    public void setUp() throws Exception {
        UserGroup.addUserToPaidGroup(paidUser);
        UserGroup.addUserToFreeGroup(freeUser);
    }

    @Test
    public void testGetWelcomeMessageForPaidUser() throws Exception {
        final String welcomeMessage = service.getWelcomeMessage(paidUser);
        final String expected = "You're amazing Jamie Coder. Thanks for paying for this awesome software.";
        assertEquals(expected,welcomeMessage);
    }

    @Test
    public void testGetWelcomeMessageForFreeUser() throws Exception {
        final String welcomeMessage = service.getWelcomeMessage(freeUser);
        final String expected = "I suppose you can use this software.";
        assertEquals(expected,welcomeMessage);
    }
}