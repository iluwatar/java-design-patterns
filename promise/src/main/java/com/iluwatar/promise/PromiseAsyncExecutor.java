package com.iluwatar.promise;

import java.util.concurrent.Callable;

public interface PromiseAsyncExecutor {
  <T> ListenableAsyncResult<T> execute(Callable<T> task);
}
