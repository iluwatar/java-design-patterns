package com.iluwatar.halfsynchalfasync;

import java.util.concurrent.Callable;

/**
 * Represents some computation that is performed asynchronously. The computation is typically
 * done is background threads and the result is posted back in form of callback.
 * 
 * @param <O> type of result
 */
public interface AsyncTask<O> extends Callable<O> {
	/**
	 * Is called in context of caller thread before call to {@link #call()}. 
	 * Validations can be performed here so that the performance penalty of context
	 * switching is not incurred.
	 */
	void preExecute();
	
	/**
	 * Is a callback which is called after the result is successfully computed by
	 * {@link #call()}.
	 */
	void onResult(O result);
	
	void onError(Throwable throwable);
	
	@Override
	O call() throws Exception;
}
