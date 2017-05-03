package concreteextensions;

import org.junit.Test;
import units.CommanderUnit;

import static org.junit.Assert.*;

/**
 * Created by Srdjan on 03-May-17.
 */
public class CommanderTest {
  @Test
  public void commanderReady() throws Exception {
    final Commander commander = new Commander(new CommanderUnit("CommanderUnitTest"));

    commander.commanderReady();
  }

}