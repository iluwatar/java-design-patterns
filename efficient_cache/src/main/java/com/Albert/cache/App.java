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
package com.Albert.cache;

import java.util.function.Function;

/**
 * @author Albert
 * @create 2018-01-10 21:33
 */
public class App {
    public static void main(String[] args) {
        Compute<Long, Long> compute = EfficientCacheCompute.<Long, Long>createNeedComputeFunction(get_A_TestComputeMethod());
        System.out.println("--------start the first task of many compute--------");
        long firstCostTime = getComputeTime(compute);
        System.out.println("This is not use cache time: " + firstCostTime + " ns");

        System.out.println("--------start the second task of many compute--------");
        long secondCostTime = getComputeTime(compute);
        System.out.println("This is use cache time: " + secondCostTime + " ns");
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

    private static long getComputeTime(Compute compute) {
        long startTime = System.nanoTime();
        App.toComputeVeryMuch(compute);
        long endTime = System.nanoTime();
        return endTime - startTime;
    }

    private static void toComputeVeryMuch(Compute compute) {
        long computeFromOneUntilThis = 100000L;
        for(long i = 1; i <= computeFromOneUntilThis; i++) {
            compute.compute(i);
        }
    }
}
