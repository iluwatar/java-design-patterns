package units;

import abstractextensions.CommanderExtension;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Srdjan on 03-May-17.
 */
public class CommanderUnitTest {
  @Test
  public void getUnitExtension() throws Exception {

    final Unit unit = new CommanderUnit("CommanderUnitName");

    assertNull(unit.getUnitExtension("SoldierExtension"));
    assertNull(unit.getUnitExtension("SergeantExtension"));
    assertNotNull((CommanderExtension) unit.getUnitExtension("CommanderExtension"));
  }

}