import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class defining Guard
 */
public class Guard implements Permission {

  private static final Logger LOGGER = LoggerFactory.getLogger(Guard.class);

  protected static void enter() {

    LOGGER.info("You can enter");
  }
}
