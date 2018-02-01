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

import java.util.concurrent.*;
import java.util.function.Function;

/**
 * @author Albert
 * @create 2018-01-10 19:44
 */
public class EfficientCacheCompute<ResultT, KeyT> implements Compute<ResultT, KeyT> {
    private final boolean IS_NOT_RETURN = true;
    private final ConcurrentHashMap<KeyT, Future<ResultT>> cacheResult;

    private final Function<KeyT, ResultT> computeMethod;

    private EfficientCacheCompute(Function<KeyT, ResultT> computeMethod) {
        this.computeMethod = computeMethod;
        this.cacheResult = new ConcurrentHashMap<>();
    }

    public static <ResultT, KeyT> EfficientCacheCompute createNeedComputeFunction(Function<KeyT, ResultT> computeMethod) {
        return new EfficientCacheCompute<>(computeMethod);
    }

    @Override
    public ResultT compute(final KeyT keyT) {
        while (IS_NOT_RETURN) {
            Future<ResultT> resultFuture = cacheResult.get(keyT);
            if (isNotExitResult(resultFuture)) {
                resultFuture = tryPutFutureToCacheAndRunCompute(keyT);
            }
            return getResultT(resultFuture);
        }
    }

    private boolean isNotExitResult(Future<ResultT> resultFuture) {
        return resultFuture == null;
    }

    private Future<ResultT> tryPutFutureToCacheAndRunCompute(KeyT keyT) {
        Message resultMessage_of_putToCache;
        resultMessage_of_putToCache = tryPutFutureToCache(keyT);
        return getFutureFromCacheAndRunComputeIfNecessary(resultMessage_of_putToCache);
    }

    private Message tryPutFutureToCache(KeyT keyT) {
        Callable<ResultT> computeMethod_Of_PutTheKey = () -> computeMethod.apply(keyT);
        FutureTask<ResultT> willPut = new FutureTask<>(computeMethod_Of_PutTheKey);
        Future<ResultT> putResult = cacheResult.putIfAbsent(keyT, willPut);
        return new Message(putResult, willPut);
    }

    private Future<ResultT> getFutureFromCacheAndRunComputeIfNecessary(Message message) {
        if (isPutSuccess(message)) {
            message.willPut.run();
            return message.willPut;
        } else {
            return message.putResult;
        }
    }

    private boolean isPutSuccess(Message message) {
        return message.putResult == null;
    }

    private ResultT getResultT(Future<ResultT> resultTFuture) {
        ResultT resultT = null;
        try {
            resultT = resultTFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return resultT;
    }

    private class Message {
        public final Future<ResultT> putResult;

        public final FutureTask<ResultT> willPut;
        public Message(Future<ResultT> putResult, FutureTask<ResultT> willPut) {
            this.putResult = putResult;
            this.willPut = willPut;
        }

    }
}
