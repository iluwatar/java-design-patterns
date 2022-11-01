package com.iluwatar.twostepview;

import iluwater.twostepview.HtmlTransformer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class HtmlTransformerTest {
    @Test
    void shouldExecuteWithoutException() {
        assertDoesNotThrow(() -> HtmlTransformer.main(new String[]{}));
    }
}
