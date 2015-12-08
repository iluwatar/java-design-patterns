package com.iluwatar.adapter;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.iluwatar.adapter.BattleFishingBoat;
import com.iluwatar.adapter.BattleShip;
import com.iluwatar.adapter.Captain;
import com.iluwatar.adapter.FishingBoat;

/**
 * Test class
 * 
 */
public class AdapterPatternTest {

  private Map<String, Object> beans;

  private static final String BATTLESHIP_BEAN = "engineer";

  private static final String CAPTAIN_BEAN = "captain";

  /**
   * This method runs before the test execution and sets the bean objects in the beans Map.
   */
  @Before
  public void setup() {
    beans = new HashMap<>();

    BattleFishingBoat battleFishingBoat = spy(new BattleFishingBoat());
    beans.put(BATTLESHIP_BEAN, battleFishingBoat);

    Captain captain = new Captain();
    captain.setBattleship((BattleFishingBoat) beans.get(BATTLESHIP_BEAN));
    beans.put(CAPTAIN_BEAN, captain);
  }

  /**
   * This test asserts that when we use the move() method on a captain bean(client), it is
   * internally calling move method on the battleship object. The Adapter ({@link BattleFishingBoat}
   * ) converts the interface of the target class ( {@link FishingBoat}) into a suitable one
   * expected by the client ({@link Captain} ).
   */
  @Test
  public void testAdapter() {
    BattleShip captain = (BattleShip) beans.get(CAPTAIN_BEAN);

    // when captain moves
    captain.move();

    // the captain internally calls the battleship object to move
    BattleShip battleship = (BattleShip) beans.get(BATTLESHIP_BEAN);
    verify(battleship).move();

    // same with above with firing
    captain.fire();
    verify(battleship).fire();

  }
}
