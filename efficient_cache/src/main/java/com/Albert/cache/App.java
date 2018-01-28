/**
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package main.java.com.Albert.cache;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

/**
 * @author Albert
 * @create 2018-01-10 21:33
 */
public class App {
    public static void main(String[] args) {

        CacheResult<Long, Long> cacheResult = BlockHardlyCacheResult.<Long, Long>createNeedComputeFunction(get_A_TestComputeMethod());

        final CountDownLatch firstComputesStartControl = new CountDownLatch(1);
        final CountDownLatch firstComputesEndMark = new CountDownLatch(1);
        final CountDownLatch secondComputesStartControl = new CountDownLatch(1);
        final CountDownLatch secondComputesEndMark = new CountDownLatch(1);

        final long computeFromOneUntilThisValue = 100000L;
        final ExecutorService executor = Executors.newFixedThreadPool(1);
        executor.execute(runManyComputeAndStartTimeIsControlled(cacheResult, firstComputesStartControl, firstComputesEndMark, computeFromOneUntilThisValue));
        getFirstExecuteTime(firstComputesStartControl, firstComputesEndMark);

        executor.execute(runManyComputeAndStartTimeIsControlled(cacheResult, secondComputesStartControl, secondComputesEndMark, computeFromOneUntilThisValue));
        getSecondExecuteTime(secondComputesStartControl, secondComputesEndMark);
        executor.shutdown();
    }

    private static Function<Long, Long> get_A_TestComputeMethod() {
        return a -> {
            long result = 0;
            for (long i = 1L; i <= a; i++) {
                result += i;
            }
            return result;
        };
    }

    private static void getSecondExecuteTime(CountDownLatch startGate2, CountDownLatch endGate2) {
        long startTime2 = System.nanoTime();
        startGate2.countDown();
        try {
            endGate2.await();
        } catch (InterruptedException e) {
            System.out.println("error........");
            e.printStackTrace();
        } finally {
            long endTime2 = System.nanoTime();
            System.out.println("This is use cache time: " + (endTime2 - startTime2) + " ns");
        }
    }

    private static void getFirstExecuteTime(CountDownLatch startGate1, CountDownLatch endGate1) {
        long startTime = System.nanoTime();
        startGate1.countDown();
        try {
            endGate1.await();
        } catch (InterruptedException e) {
            System.out.println("error........");
            e.printStackTrace();
        } finally {
            long endTime = System.nanoTime();
            System.out.println("This is not use cache time: " + (endTime - startTime) + " ns");
        }
    }

    private static Runnable runManyComputeAndStartTimeIsControlled(CacheResult<Long, Long> cacheResult, CountDownLatch startGate2, CountDownLatch endGate2, long computeFromOneUntilThis) {
        return () -> {
            try {
                startGate2.await();
                for(long i = 1; i <= computeFromOneUntilThis; i++) {
                    cacheResult.compute(i);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                endGate2.countDown();
            }
        };
    }
}
