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

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class CreditCardTest {
  CreditCard card;
  CreditContractVersion contract1;
  CreditContractVersion contract2;

  @BeforeEach
  public void setup(){
    SimpleDate.setToday(new SimpleDate(0,0,0));
    contract1 = new CreditContractVersion("1", "bank1", "1", 100, 12345,
            1234, new SimpleDate(1, 1, 1));

    card = new CreditCard(contract1, new SimpleDate(0,0,0));

    SimpleDate.setToday(new SimpleDate(1,1,0));

    contract2 = new CreditContractVersion("2", "bank2", "2", 200, 12345,
            1234, new SimpleDate(2,2,2));
    card.addContract(contract2, new SimpleDate(2, 1, 1));
  }

  public void correctValues(){
    SimpleDate.setToday(new SimpleDate(0,0,0));
    assertEquals(100, card.getCreditLimit());
    assertEquals(contract1, card.getContract());
    assertEquals(1234, card.getCvc());
    assertEquals(new SimpleDate(1, 1, 1), card.getExpiration());
    assertEquals(12345, card.getNumber());
    assertFalse(card.isExpired());

    SimpleDate.setToday(new SimpleDate(1,1,1));
    assertEquals(200, card.getCreditLimit());
    assertEquals(contract2, card.getContract());
    assertEquals(1234, card.getCvc());
    assertEquals(new SimpleDate(2, 2, 2), card.getExpiration());
    assertEquals(12345, card.getNumber());
    assertFalse(card.isExpired());

    SimpleDate.setToday(new SimpleDate(20, 20, 20));
    assertTrue(card.isExpired());
  }
}
