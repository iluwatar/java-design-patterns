import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.iluwatar.health.check.App;
import org.junit.jupiter.api.Test;

/** Application test */
class AppTest {

  /** Entry point */
  @Test
  void shouldExecuteApplicationWithoutException() {
    assertDoesNotThrow(() -> App.main(new String[] {}));
  }
}
