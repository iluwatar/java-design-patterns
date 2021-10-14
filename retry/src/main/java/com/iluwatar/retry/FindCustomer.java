/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.retry;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * Finds a customer, returning its ID from our records.
 *
 * <p>This is an imaginary operation that, for some imagined input, returns the ID for a customer.
 * However, this is a "flaky" operation that is supposed to fail intermittently, but for the
 * purposes of this example it fails in a programmed way depending on the constructor parameters.
 *
 * @author George Aristy (george.aristy@gmail.com)
 */
public final class FindCustomer implements BusinessOperation<String> {
  private final String customerId;
  private final Deque<BusinessException> errors;

  /**
   * Ctor.
   *
   * @param customerId the final result of the remote operation
   * @param errors     the errors to throw before returning {@code customerId}
   */
  public FindCustomer(String customerId, BusinessException... errors) {
    this.customerId = customerId;
    this.errors = new ArrayDeque<>(List.of(errors));
  }

  @Override
  public String perform() throws BusinessException {
    if (!this.errors.isEmpty()) {
      throw this.errors.pop();
    }

    return this.customerId;
  }
}
