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

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreditCardContractTest {
  @Test
  public void correctValues(){
    String contractText = "body text";
    String contractIssuer = "bank";
    String version = "1";
    int creditLimit = 9999;
    int cardNumber = 12345;
    int cvcCode = 1234;
    SimpleDate expirationDate = new SimpleDate(0,0,0);

    CreditContractVersion contractVersion = new CreditContractVersion(contractText, contractIssuer, version, creditLimit,
            cardNumber, cvcCode, expirationDate);

    assertEquals(contractText, contractVersion.getContractText());
    assertEquals(contractIssuer, contractVersion.getContractIssuer());
    assertEquals(version, contractVersion.getVersion());
    assertEquals(creditLimit, contractVersion.getCreditLimit());
    assertEquals(cardNumber, contractVersion.getCardNumber());
    assertEquals(cvcCode, contractVersion.getCvcCode());
    assertEquals(expirationDate, contractVersion.getExpirationDate());
  }
}
