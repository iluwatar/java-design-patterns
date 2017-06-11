package concreteextensions;

import org.junit.Test;
import units.SergeantUnit;

/**
 * Created by Srdjan on 03-May-17.
 */
public class SergeantTest {
  @Test
  public void sergeantReady() throws Exception {
    final Sergeant sergeant = new Sergeant(new SergeantUnit("SergeantUnitTest"));

    sergeant.sergeantReady();
  }

}