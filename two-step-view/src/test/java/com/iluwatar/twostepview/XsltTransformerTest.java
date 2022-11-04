package com.iluwatar.twostepview;

import com.iluwater.twostepview.XsltTransformer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class XsltTransformerTest {
    @Test
    void shouldExecuteWithoutException() {
        assertDoesNotThrow(() -> XsltTransformer.main(new String[]{}));
    }
}
