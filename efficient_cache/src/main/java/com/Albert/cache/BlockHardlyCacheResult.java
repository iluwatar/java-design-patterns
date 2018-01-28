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

import java.util.concurrent.*;
import java.util.function.Function;

/**
 * @author Albert
 * @create 2018-01-10 19:44
 */
public class BlockHardlyCacheResult<ResultT, KeyT> implements CacheResult<ResultT, KeyT> {
    private final boolean IS_NOT_RETURN = true;
    private final ConcurrentHashMap<KeyT, Future<ResultT>> cacheResult;

    private final Function<KeyT, ResultT> computeMethod;

    private BlockHardlyCacheResult(Function<KeyT, ResultT> computeMethod) {
        this.computeMethod = computeMethod;
        this.cacheResult = new ConcurrentHashMap<>();
    }

    public static <ResultT, KeyT> BlockHardlyCacheResult createNeedComputeFunction(Function<KeyT, ResultT> computeMethod) {
        return new BlockHardlyCacheResult<>(computeMethod);
    }

    @Override
    public ResultT compute(final KeyT keyT) {
        while (IS_NOT_RETURN) {
            Future<ResultT> resultFuture = cacheResult.get(keyT);
            if (isNotExitResult(resultFuture)) {
                Callable<ResultT> putKeyComputeMethod = () -> computeMethod.apply(keyT);
                FutureTask<ResultT> runWhenResultFutureNull = new FutureTask<>(putKeyComputeMethod);
                resultFuture = cacheResult.putIfAbsent(keyT, runWhenResultFutureNull);
                if (isNotExitResult(resultFuture)) {
                    resultFuture = runWhenResultFutureNull;
                    runWhenResultFutureNull.run();
                }
            }
            try {
                return resultFuture.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isNotExitResult(Future<ResultT> resultFuture) {
        return resultFuture == null;
    }
}
