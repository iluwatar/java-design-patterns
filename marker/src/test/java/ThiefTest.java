/**
 * Created by Alexis on 02-May-17.
 */

import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class ThiefTest {
  @Test
  public void testGuard() {
    Thief thief = new Thief();
    assertFalse(thief instanceof Permission);
  }
}
