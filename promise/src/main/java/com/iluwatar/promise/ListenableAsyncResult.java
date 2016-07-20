package com.iluwatar.promise;

import java.util.function.Consumer;
import java.util.function.Function;

import com.iluwatar.async.method.invocation.AsyncResult;

public interface ListenableAsyncResult<T> extends AsyncResult<T> {
  ListenableAsyncResult<Void> then(Consumer<? super T> action);
  <V> ListenableAsyncResult<V> then(Function<? super T, V> func);
  ListenableAsyncResult<T> error(Consumer<? extends Throwable> action);
}
