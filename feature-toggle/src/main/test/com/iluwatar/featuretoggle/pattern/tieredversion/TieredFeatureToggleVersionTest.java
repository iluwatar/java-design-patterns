package com.iluwatar.featuretoggle.pattern.tieredversion;

import com.iluwatar.featuretoggle.pattern.Service;
import com.iluwatar.featuretoggle.user.User;
import com.iluwatar.featuretoggle.user.UserGroup;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by joseph on 26/01/16.
 */
public class TieredFeatureToggleVersionTest {

    User paidUser = new User();
    User freeUser = new User();

    @Before
    public void setUp() throws Exception {
        UserGroup.addUserToPaidGroup(paidUser);
        UserGroup.addUserToFreeGroup(freeUser);

    }

    @Test
    public void testGetWelcomeMessageForPaidUser() throws Exception {
        Service service = new TieredFeatureToggleVersion();
        String welcomeMessage = service.getWelcomeMessage(paidUser);
        assertEquals("You're amazing thanks for paying for this awesome software.",welcomeMessage);
    }

    @Test
    public void testGetWelcomeMessageForFreeUser() throws Exception {
        Service service = new TieredFeatureToggleVersion();
        String welcomeMessage = service.getWelcomeMessage(freeUser);
        assertEquals("I suppose you can use this software.",welcomeMessage);
    }
}