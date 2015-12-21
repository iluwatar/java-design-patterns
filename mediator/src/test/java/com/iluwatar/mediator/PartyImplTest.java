package com.iluwatar.mediator;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

/**
 * Date: 12/19/15 - 10:00 PM
 *
 * @author Jeroen Meulemeester
 */
public class PartyImplTest {

  /**
   * Verify if a member is notified when it's joining a party. Generate an action and see if the
   * other member gets it. Also check members don't get their own actions.
   */
  @Test
  public void testPartyAction() {
    final PartyMember partyMember1 = mock(PartyMember.class);
    final PartyMember partyMember2 = mock(PartyMember.class);

    final PartyImpl party = new PartyImpl();
    party.addMember(partyMember1);
    party.addMember(partyMember2);

    verify(partyMember1).joinedParty(party);
    verify(partyMember2).joinedParty(party);

    party.act(partyMember1, Action.GOLD);
    verifyZeroInteractions(partyMember1);
    verify(partyMember2).partyAction(Action.GOLD);

    verifyNoMoreInteractions(partyMember1, partyMember2);

  }

}
