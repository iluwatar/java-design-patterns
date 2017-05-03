import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * Created by Alexis on 02-May-17.
 */
public class ThiefTest {
  @Test
  public void testGuard() {
    Thief thief = new Thief();
    assertFalse(thief instanceof Permission);
  }
}
