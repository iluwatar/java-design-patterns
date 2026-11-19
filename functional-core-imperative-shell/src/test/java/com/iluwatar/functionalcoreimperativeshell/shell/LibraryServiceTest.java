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
package com.iluwatar.functionalcoreimperativeshell.shell;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.iluwatar.functionalcoreimperativeshell.core.Loan;
import com.iluwatar.functionalcoreimperativeshell.core.Member;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Integration tests for the shell: they run the real store and the real core together and check
 * that the shell reads the clock, persists the core's decisions and closes loans.
 */
class LibraryServiceTest {

  private static final Instant MARCH_1 = Instant.parse("2024-03-01T10:00:00Z");

  private LibraryStore store;

  @BeforeEach
  void setUp() {
    store = new LibraryStore();
  }

  private LibraryService serviceOn(Instant now) {
    return new LibraryService(store, Clock.fixed(now, ZoneOffset.UTC));
  }

  @Test
  void borrowUsesClockAndStoresLoan() {
    var library = serviceOn(MARCH_1);
    library.register("alice");

    var loan = library.borrow("alice", "Dune").orElseThrow();

    assertEquals(new Loan("LOAN-1", "alice", "Dune", LocalDate.of(2024, 3, 15)), loan);
    assertEquals(loan, store.findLoan("LOAN-1").orElseThrow());
  }

  @Test
  void lateReturnPersistsFineAndClosesLoan() {
    serviceOn(MARCH_1).register("alice");
    var loan = serviceOn(MARCH_1).borrow("alice", "Dune").orElseThrow();

    var outcome = serviceOn(Instant.parse("2024-03-19T10:00:00Z")).returnBook(loan.id());

    assertEquals(4, outcome.daysLate());
    assertEquals(new Member("alice", 100), store.findMember("alice").orElseThrow());
    assertTrue(store.findLoan(loan.id()).isEmpty());
    assertEquals(0, store.openLoanCount());
  }

  @Test
  void borrowIsRefusedOnceFinesReachLimit() {
    var library = serviceOn(MARCH_1);
    store.saveMember(new Member("alice", 1_000));

    assertTrue(library.borrow("alice", "Dune").isEmpty());
    assertEquals(0, store.openLoanCount());
  }

  @Test
  void loanIdsStayUniqueAcrossServicesSharingStore() {
    serviceOn(MARCH_1).register("alice");

    var first = serviceOn(MARCH_1).borrow("alice", "Dune").orElseThrow();
    var second = serviceOn(MARCH_1).borrow("alice", "Emma").orElseThrow();

    assertEquals("LOAN-1", first.id());
    assertEquals("LOAN-2", second.id());
  }

  @Test
  void unknownMemberOrLoanIsRejected() {
    var library = serviceOn(MARCH_1);

    assertThrows(IllegalArgumentException.class, () -> library.borrow("nobody", "Dune"));
    assertThrows(IllegalArgumentException.class, () -> library.returnBook("LOAN-404"));
  }
}
