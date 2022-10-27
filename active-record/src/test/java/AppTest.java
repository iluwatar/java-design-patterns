import com.iluwatar.activeobject.App;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AppTest {
    @Test
    void runWithoutException() {
        assertDoesNotThrow(()-> App.main(new String[]{}));
    }
}