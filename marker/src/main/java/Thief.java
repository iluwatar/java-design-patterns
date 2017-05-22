import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class defining Thief
 */
public class Thief {

  private static final Logger LOGGER = LoggerFactory.getLogger(Thief.class);

  protected static void steal() {
    LOGGER.info("Steal valuable items");
  }

  protected static void doNothing() {
    LOGGER.info("Pretend nothing happened and just leave");
  }
}
