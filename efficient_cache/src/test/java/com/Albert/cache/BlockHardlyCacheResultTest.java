package com.Albert.cache;

import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BlockHardlyCacheResultTest {

    @Test
    public void testSuccessfulCompute() {
        CacheResult<Integer, Integer> cacheResult = BlockHardlyCacheResult.<Integer, Integer>createNeedComputeFunction(get_A_TestComputeMethod());
        final int key = 1;
        final int realityValue = 2;
        int assertValue = cacheResult.compute(1);
        assertEquals(assertValue, realityValue);
    }

    private Function<Integer, Integer> get_A_TestComputeMethod() {
        return key -> key += 1;
    }
}