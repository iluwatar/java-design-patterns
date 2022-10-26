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

package com.iluwatar.temporalobject;

import lombok.extern.slf4j.Slf4j;

/**
 * The Temporal Object pattern is an object that changes over time.
 *
 * <p>There are times where it is useful to consider that an object that has
 * properties that change over time, and others where the object is temporal. </p>
 *
 * <p>In this particular example, each {@link CreditCard} has a
 * {@link TemporalCollection}, which stores the prior and current
 * contract terms as {@link CreditContractVersion}s. In this case, most details about the credit
 * card are part of the contract, and so, the {@link CreditCard} is considered a Temporal Object
 * .</p>
 */
@Slf4j
public class App {
  /**
   * Main function.
   *
   * @param args Unused arguments.
   */
  public static void main(String[] args) {
    SimpleDate.setToday(new SimpleDate(2000, 3, 1));

    // start a credit card contract from today until 3rd of September 2002
    CreditContractVersion firstContract = new CreditContractVersion("Original Contract",
            "Bank of XYZ", "Version 1", 100, 12345, 1234,
            new SimpleDate(2002, 9, 3));
    CreditCard card = new CreditCard(firstContract, SimpleDate.getToday());
    LOGGER.info("New card with limit of " + card.getCreditLimit() + ", card number "
            + card.getNumber() + ", and cvc code of " + card.getCvc() + " which expires on "
            + card.getExpiration());

    // The card expires
    SimpleDate.setToday(new SimpleDate(2002, 9, 18));
    LOGGER.info(SimpleDate.getToday().toString() + " is expired: " + card.isExpired());

    // And is then re-negotiated, with the new contract coming into effect a few days later

    CreditContractVersion secondContract = new CreditContractVersion("new contract", "Bank of XYZ",
            "Version 2", 120, 12345, 1234, new SimpleDate(2004, 3, 4));
    card.addContract(secondContract, new SimpleDate(2002, 9, 21));

    // A few days later when the new contract is effective
    SimpleDate.setToday(new SimpleDate(2002, 9, 21));
    LOGGER.info("Re-negotiated card with limit of " + card.getCreditLimit() + ", card number "
            + card.getNumber() + ", and cvc code of " + card.getCvc() + " which expires on "
            + card.getExpiration());
    LOGGER.info(SimpleDate.getToday().toString() + " is expired: " + card.isExpired());
  }
}
