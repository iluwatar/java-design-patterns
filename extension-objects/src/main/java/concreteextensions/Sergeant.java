package concreteextensions;

import abstractextensions.SergeantExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import units.SergeantUnit;

/**
 * Created by Srdjan on 27-Apr-17.
 */
public class Sergeant implements SergeantExtension {

  private SergeantUnit unit;

  public Sergeant(SergeantUnit sergeantUnit) {
    this.unit = sergeantUnit;
  }

  final Logger logger = LoggerFactory.getLogger(Sergeant.class);

  @Override
  public void sergeantReady() {
    logger.info("[Sergeant] " + unit.getName() + " is ready! ");
  }
}
