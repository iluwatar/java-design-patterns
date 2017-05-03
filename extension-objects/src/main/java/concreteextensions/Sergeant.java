package concreteextensions;

import abstractextensions.SergeantExtension;
import units.SergeantUnit;

/**
 * Created by Srdjan on 27-Apr-17.
 */
public class Sergeant implements SergeantExtension {

  private SergeantUnit unit;

  public Sergeant(SergeantUnit sergeantUnit) {
    this.unit = sergeantUnit;
  }

  @Override
  public void sergeantReady() {
    System.out.println("[Sergeant] " + unit.getName() + " is ready! ");
  }
}
