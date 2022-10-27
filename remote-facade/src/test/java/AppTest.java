
import com.iluwatar.remote.facade.Client;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class AppTest {

    @Test
    void runApplicationWithoutException() {
        assertDoesNotThrow(() -> Client.main(new String[]{}));
    }
}
