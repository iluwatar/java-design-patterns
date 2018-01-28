package test.java.com.Albert.cache;

import main.java.com.Albert.cache.BlockHardlyCacheResult;
import main.java.com.Albert.cache.CacheResult;
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