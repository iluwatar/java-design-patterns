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

package com.iluwatar.retry;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link FindCustomer}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 */
public class FindCustomerTest {
  /**
   * Returns the given result with no exceptions.
   */
  @Test
  public void noExceptions() throws Exception {
    assertThat(
        new FindCustomer("123").perform(),
        is("123")
    );
  }

  /**
   * Throws the given exception.
   * 
   * @throws Exception the expected exception
   */
  @Test
  public void oneException() {
    assertThrows(BusinessException.class, () -> {
      new FindCustomer("123", new BusinessException("test")).perform();
    });
  }

  /**
   * Should first throw the given exceptions, then return the given result.
   * 
   * @throws Exception not an expected exception
   */
  @Test
  public void resultAfterExceptions() throws Exception {
    final BusinessOperation<String> op = new FindCustomer(
        "123", 
        new CustomerNotFoundException("not found"),
        new DatabaseNotAvailableException("not available")
    );
    try {
      op.perform();
    } catch (CustomerNotFoundException e) {
      //ignore
    }
    try {
      op.perform();
    } catch (DatabaseNotAvailableException e) {
      //ignore
    }

    assertThat(
        op.perform(),
        is("123")
    );
  }
}
