package com.iluwatar.pipeline;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class PipelineTest {
    @Test
    public void testStringFiltersPipeline() {
        Pipeline<String, char[]> filters = new Pipeline<>(new RemoveAlphabetsHandler())
                .addHandler(new RemoveDigitsHandler())
                .addHandler(new ConvertToCharArrayHandler());

        assertArrayEquals(
                new char[]{'#', '!', '(', '&', '%', '#', '!'},
                filters.execute("#H!E(L&L0O%THE3R#34E!")
        );
    }
}
