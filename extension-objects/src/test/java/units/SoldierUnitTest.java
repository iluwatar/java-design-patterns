package units;

import abstractextensions.SoldierExtension;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Srdjan on 03-May-17.
 */
public class SoldierUnitTest {
  @Test
  public void getUnitExtension() throws Exception {

    final Unit unit = new SoldierUnit("SoldierUnitName");

    assertNotNull((SoldierExtension) unit.getUnitExtension("SoldierExtension"));
    assertNull(unit.getUnitExtension("SergeantExtension"));
    assertNull(unit.getUnitExtension("CommanderExtension"));


  }

}