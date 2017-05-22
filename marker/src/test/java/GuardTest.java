import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * Guard test
 */
public class GuardTest {

  @Test
  public void testGuard() {
    Guard guard = new Guard();
    assertThat(guard, instanceOf(Permission.class));
  }
}