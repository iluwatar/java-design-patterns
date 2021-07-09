/**
 * Created by anditty on 2021/5/21 9:33 下午
 */

package com.iluwatar.thelayersupertypepattern;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


public class AppTest {
    @Test
    void shouldExecuteWithoutException() {
        assertDoesNotThrow(() -> App.main(new String[]{}));
    }
}
