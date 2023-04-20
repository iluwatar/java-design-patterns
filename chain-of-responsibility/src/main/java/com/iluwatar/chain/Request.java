/*
 * This project is licensed under the MIT license. Module model-view-viewmodel is using ZK framework licensed under LGPL (see lgpl-3.0.txt).
 *
 * The MIT License
 * Copyright © 2014-2022 Ilkka Seppälä
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
package com.iluwatar.chain;

import java.util.Objects;

/**
 * Request.
 */
public class Request {

  /**
   * The type of this request, used by each item in the chain to see if they should or can handle
   * this particular request.
   * 此请求的类型，由链中的每个项使用，以查看它们是否应该或能够处理此特定请求。
   */
  private final RequestType requestType;

  /**
   * A description of the request.
   * 请求的描述。
   */
  private final String requestDescription;

  /**
   * Indicates if the request is handled or not. A request can only switch state from unhandled to
   * handled, there's no way to 'unhandle' a request.
   * 指示请求是否被处理。请求只能从未处理状态切换到已处理状态，没有办法“unhandle”请求。
   */
  private boolean handled;

  /**
   * Create a new request of the given type and accompanied description.
   * 创建给定类型和附带说明的新请求。
   *
   * @param requestType        The type of request
   * @param requestDescription The description of the request
   */
  public Request(final RequestType requestType, final String requestDescription) {
    this.requestType = Objects.requireNonNull(requestType);
    this.requestDescription = Objects.requireNonNull(requestDescription);
  }

  /**
   * Get a description of the request.
   * 获取请求的描述。
   *
   * @return A human readable description of the request
   */
  public String getRequestDescription() {
    return requestDescription;
  }

  /**
   * Get the type of this request, used by each person in the chain of command to see if they should
   * or can handle this particular request.
   * 获取此请求的类型，命令链中的每个人使用该类型，以查看他们是否应该或能够处理此特定请求。
   *
   * @return The request type
   */
  public RequestType getRequestType() {
    return requestType;
  }

  /**
   * Mark the request as handled.
   * 将请求标记为已处理。
   */
  public void markHandled() {
    this.handled = true;
  }

  /**
   * Indicates if this request is handled or not.
   * 指示是否处理此请求。
   *
   * @return <tt>true</tt> when the request is handled, <tt>false</tt> if not
   */
  public boolean isHandled() {
    return this.handled;
  }

  @Override
  public String toString() {
    return getRequestDescription();
  }

}
