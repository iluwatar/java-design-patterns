package units;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Srdjan on 03-May-17.
 */
public class UnitTest {

  @Test
  public void testConstGetSet() throws Exception {
    final String name = "testName";
    final Unit unit = new Unit(name);
    assertEquals(name,unit.getName());

    final String newName = "newName";
    unit.setName(newName);
    assertEquals(newName,unit.getName());


    assertNull(unit.getUnitExtension(""));
    assertNull(unit.getUnitExtension("SoldierExtension"));
    assertNull(unit.getUnitExtension("SergeantExtension"));
    assertNull(unit.getUnitExtension("CommanderExtension"));
  }

}