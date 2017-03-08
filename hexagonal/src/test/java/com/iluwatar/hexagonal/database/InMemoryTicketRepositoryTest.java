/**
 * The MIT License
 * Copyright (c) 2014-2016 Ilkka Seppälä
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
package com.iluwatar.hexagonal.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import com.iluwatar.hexagonal.domain.LotteryTicket;
import com.iluwatar.hexagonal.domain.LotteryTicketId;
import com.iluwatar.hexagonal.test.LotteryTestUtils;

/**
 * 
 * Tests for {@link LotteryTicketRepository}
 *
 */
public class InMemoryTicketRepositoryTest {

  private final LotteryTicketRepository repository = new InMemoryTicketRepository();
  
  @Before
  public void clear() {
    repository.deleteAll();
  }
  
  @Test
  public void testCrudOperations() {
    LotteryTicketRepository repository = new InMemoryTicketRepository();
    assertEquals(repository.findAll().size(), 0);
    LotteryTicket ticket = LotteryTestUtils.createLotteryTicket();
    Optional<LotteryTicketId> id = repository.save(ticket);
    assertTrue(id.isPresent());
    assertEquals(repository.findAll().size(), 1);
    Optional<LotteryTicket> optionalTicket = repository.findById(id.get());
    assertTrue(optionalTicket.isPresent());
  }
}
