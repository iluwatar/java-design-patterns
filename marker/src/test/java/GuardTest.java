import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * Created by Alexis on 02-May-17.
 */
public class GuardTest {

  @Test
  public void testGuard() {
    Guard guard = new Guard();
    assertThat(guard, instanceOf(Permission.class));
  }
}
