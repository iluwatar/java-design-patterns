import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * Thief test
 */
public class ThiefTest {
  @Test
  public void testThief() {
    Thief thief = new Thief();
    assertFalse(thief instanceof Permission);
  }
}