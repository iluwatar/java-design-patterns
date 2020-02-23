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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The <em>Retry</em> pattern enables applications to handle potentially recoverable failures from
 * the environment if the business requirements and nature of the failures allow it. By retrying
 * failed operations on external dependencies, the application may maintain stability and minimize
 * negative impact on the user experience.
 *
 * <p>In our example, we have the {@link BusinessOperation} interface as an abstraction over all
 * operations that our application performs involving remote systems. The calling code should remain
 * decoupled from implementations.
 *
 * <p>{@link FindCustomer} is a business operation that looks up a customer's record and returns
 * its ID. Imagine its job is performed by looking up the customer in our local database and
 * returning its ID. We can pass {@link CustomerNotFoundException} as one of its {@link
 * FindCustomer#FindCustomer(java.lang.String, com.iluwatar.retry.BusinessException...) constructor
 * parameters} in order to simulate not finding the customer.
 *
 * <p>Imagine that, lately, this operation has experienced intermittent failures due to some weird
 * corruption and/or locking in the data. After retrying a few times the customer is found. The
 * database is still, however, expected to always be available.  While a definitive solution is
 * found to the problem, our engineers advise us to retry the operation a set number of times with a
 * set delay between retries, although not too many retries otherwise the end user will be left
 * waiting for a long time, while delays that are too short will not allow the database to recover
 * from the load.
 *
 * <p>To keep the calling code as decoupled as possible from this workaround, we have implemented
 * the retry mechanism as a {@link BusinessOperation} named {@link Retry}.
 *
 * @author George Aristy (george.aristy@gmail.com)
 * @see <a href="https://docs.microsoft.com/en-us/azure/architecture/patterns/retry">Retry pattern
 *     (Microsoft Azure Docs)</a>
 */
public final class App {
  private static final Logger LOG = LoggerFactory.getLogger(App.class);
  private static BusinessOperation<String> op;

  /**
   * Entry point.
   *
   * @param args not used
   * @throws Exception not expected
   */
  public static void main(String[] args) throws Exception {
    noErrors();
    errorNoRetry();
    errorWithRetry();
    errorWithRetryExponentialBackoff();
  }

  private static void noErrors() throws Exception {
    op = new FindCustomer("123");
    op.perform();
    LOG.info("Sometimes the operation executes with no errors.");
  }

  private static void errorNoRetry() throws Exception {
    op = new FindCustomer("123", new CustomerNotFoundException("not found"));
    try {
      op.perform();
    } catch (CustomerNotFoundException e) {
      LOG.info("Yet the operation will throw an error every once in a while.");
    }
  }

  private static void errorWithRetry() throws Exception {
    final var retry = new Retry<>(
        new FindCustomer("123", new CustomerNotFoundException("not found")),
        3,  //3 attempts
        100, //100 ms delay between attempts
        e -> CustomerNotFoundException.class.isAssignableFrom(e.getClass())
    );
    op = retry;
    final var customerId = op.perform();
    LOG.info(String.format(
        "However, retrying the operation while ignoring a recoverable error will eventually yield "
            + "the result %s after a number of attempts %s", customerId, retry.attempts()
    ));
  }

  private static void errorWithRetryExponentialBackoff() throws Exception {
    final var retry = new RetryExponentialBackoff<>(
        new FindCustomer("123", new CustomerNotFoundException("not found")),
        6,  //6 attempts
        30000, //30 s max delay between attempts
        e -> CustomerNotFoundException.class.isAssignableFrom(e.getClass())
    );
    op = retry;
    final var customerId = op.perform();
    LOG.info(String.format(
        "However, retrying the operation while ignoring a recoverable error will eventually yield "
            + "the result %s after a number of attempts %s", customerId, retry.attempts()
    ));
  }
}
