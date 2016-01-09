package com.iluwatar.strategy;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

/**
 * Date: 12/29/15 - 10:50 PM
 *
 * @author Jeroen Meulemeester
 */
public class DragonSlayerTest {

  /**
   * Verify if the dragon slayer uses the strategy during battle
   */
  @Test
  public void testGoToBattle() {
    final DragonSlayingStrategy strategy = mock(DragonSlayingStrategy.class);
    final DragonSlayer dragonSlayer = new DragonSlayer(strategy);

    dragonSlayer.goToBattle();
    verify(strategy).execute();
    verifyNoMoreInteractions(strategy);
  }

  /**
   * Verify if the dragon slayer uses the new strategy during battle after a change of strategy
   */
  @Test
  public void testChangeStrategy() throws Exception {
    final DragonSlayingStrategy initialStrategy = mock(DragonSlayingStrategy.class);
    final DragonSlayer dragonSlayer = new DragonSlayer(initialStrategy);

    dragonSlayer.goToBattle();
    verify(initialStrategy).execute();

    final DragonSlayingStrategy newStrategy = mock(DragonSlayingStrategy.class);
    dragonSlayer.changeStrategy(newStrategy);

    dragonSlayer.goToBattle();
    verify(newStrategy).execute();

    verifyNoMoreInteractions(initialStrategy, newStrategy);
  }
}