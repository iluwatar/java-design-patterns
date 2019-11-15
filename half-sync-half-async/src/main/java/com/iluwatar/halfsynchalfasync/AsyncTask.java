/*
 * The MIT License
 * Copyright © 2014-2019 Ilkka Seppälä
 *
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

package com.iluwatar.halfsynchalfasync;

import java.util.concurrent.Callable;

/**
 * Represents some computation that is performed asynchronously and its result. The computation is
 * typically done is background threads and the result is posted back in form of callback. The
 * callback does not implement {@code isComplete}, {@code cancel} as it is out of scope of this
 * pattern.
 *
 * @param <O> type of result
 */
public interface AsyncTask<O> extends Callable<O> {
  /**
   * Is called in context of caller thread before call to {@link #call()}. Large tasks should not be
   * performed in this method as it will block the caller thread. Small tasks such as validations
   * can be performed here so that the performance penalty of context switching is not incurred in
   * case of invalid requests.
   */
  void onPreCall();

  /**
   * A callback called after the result is successfully computed by {@link #call()}. In our
   * implementation this method is called in context of background thread but in some variants, such
   * as Android where only UI thread can change the state of UI widgets, this method is called in
   * context of UI thread.
   */
  void onPostCall(O result);

  /**
   * A callback called if computing the task resulted in some exception. This method is called when
   * either of {@link #call()} or {@link #onPreCall()} throw any exception.
   *
   * @param throwable error cause
   */
  void onError(Throwable throwable);

  /**
   * This is where the computation of task should reside. This method is called in context of
   * background thread.
   */
  @Override
  O call() throws Exception;
}
