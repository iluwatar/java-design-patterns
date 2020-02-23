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

package com.iluwatar.hexagonal.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Set;
import org.junit.jupiter.api.Test;

/**
 * Test Lottery Tickets for equality
 */
class LotteryTicketTest {

  @Test
  void testEquals() {
    var details1 = new PlayerDetails("bob@foo.bar", "1212-121212", "+34332322");
    var numbers1 = LotteryNumbers.create(Set.of(1, 2, 3, 4));
    var ticket1 = new LotteryTicket(new LotteryTicketId(), details1, numbers1);
    var details2 = new PlayerDetails("bob@foo.bar", "1212-121212", "+34332322");
    var numbers2 = LotteryNumbers.create(Set.of(1, 2, 3, 4));
    var ticket2 = new LotteryTicket(new LotteryTicketId(), details2, numbers2);
    assertEquals(ticket1, ticket2);
    var details3 = new PlayerDetails("elsa@foo.bar", "1223-121212", "+49332322");
    var numbers3 = LotteryNumbers.create(Set.of(1, 2, 3, 8));
    var ticket3 = new LotteryTicket(new LotteryTicketId(), details3, numbers3);
    assertNotEquals(ticket1, ticket3);
  }
}
