package com.iluwatar.prototype;

import org.junit.Test;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * Date: 12/28/15 - 8:34 PM
 *
 * @author Jeroen Meulemeester
 */
public class HeroFactoryImplTest {

  @Test
  public void testFactory() throws Exception {
    final Mage mage = mock(Mage.class);
    final Warlord warlord = mock(Warlord.class);
    final Beast beast = mock(Beast.class);

    when(mage.clone()).thenThrow(CloneNotSupportedException.class);
    when(warlord.clone()).thenThrow(CloneNotSupportedException.class);
    when(beast.clone()).thenThrow(CloneNotSupportedException.class);

    final HeroFactoryImpl factory = new HeroFactoryImpl(mage, warlord, beast);
    assertNull(factory.createMage());
    assertNull(factory.createWarlord());
    assertNull(factory.createBeast());

    verify(mage).clone();
    verify(warlord).clone();
    verify(beast).clone();
    verifyNoMoreInteractions(mage, warlord, beast);
  }

}