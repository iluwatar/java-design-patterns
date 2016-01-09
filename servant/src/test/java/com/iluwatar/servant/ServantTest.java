package com.iluwatar.servant;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Date: 12/28/15 - 10:02 PM
 *
 * @author Jeroen Meulemeester
 */
public class ServantTest {

  @Test
  public void testFeed() throws Exception {
    final Royalty royalty = mock(Royalty.class);
    final Servant servant = new Servant("test");
    servant.feed(royalty);
    verify(royalty).getFed();
    verifyNoMoreInteractions(royalty);
  }

  @Test
  public void testGiveWine() throws Exception {
    final Royalty royalty = mock(Royalty.class);
    final Servant servant = new Servant("test");
    servant.giveWine(royalty);
    verify(royalty).getDrink();
    verifyNoMoreInteractions(royalty);
  }

  @Test
  public void testGiveCompliments() throws Exception {
    final Royalty royalty = mock(Royalty.class);
    final Servant servant = new Servant("test");
    servant.giveCompliments(royalty);
    verify(royalty).receiveCompliments();
    verifyNoMoreInteractions(royalty);
  }

  @Test
  public void testCheckIfYouWillBeHanged() throws Exception {
    final Royalty goodMoodRoyalty = mock(Royalty.class);
    when(goodMoodRoyalty.getMood()).thenReturn(true);

    final Royalty badMoodRoyalty = mock(Royalty.class);
    when(badMoodRoyalty.getMood()).thenReturn(true);

    final ArrayList<Royalty> goodCompany = new ArrayList<>();
    goodCompany.add(goodMoodRoyalty);
    goodCompany.add(goodMoodRoyalty);
    goodCompany.add(goodMoodRoyalty);

    final ArrayList<Royalty> badCompany = new ArrayList<>();
    goodCompany.add(goodMoodRoyalty);
    goodCompany.add(goodMoodRoyalty);
    goodCompany.add(badMoodRoyalty);

    assertTrue(new Servant("test").checkIfYouWillBeHanged(goodCompany));
    assertTrue(new Servant("test").checkIfYouWillBeHanged(badCompany));

  }

}