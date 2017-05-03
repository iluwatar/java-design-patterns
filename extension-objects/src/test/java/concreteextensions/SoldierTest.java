package concreteextensions;

import org.junit.Test;
import units.SoldierUnit;

import static org.junit.Assert.*;

/**
 * Created by Srdjan on 03-May-17.
 */
public class SoldierTest {
  @Test
  public void soldierReady() throws Exception {
    final Soldier soldier = new Soldier(new SoldierUnit("SoldierUnitTest"));

    soldier.soldierReady();
  }

}