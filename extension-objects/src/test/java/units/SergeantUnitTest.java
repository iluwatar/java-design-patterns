package units;

import abstractextensions.SergeantExtension;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Srdjan on 03-May-17.
 */
public class SergeantUnitTest {
  @Test
  public void getUnitExtension() throws Exception {

    final Unit unit = new SergeantUnit("SergeantUnitName");

    assertNull(unit.getUnitExtension("SoldierExtension"));
    assertNotNull((SergeantExtension)unit.getUnitExtension("SergeantExtension"));
    assertNull(unit.getUnitExtension("CommanderExtension"));
  }

}