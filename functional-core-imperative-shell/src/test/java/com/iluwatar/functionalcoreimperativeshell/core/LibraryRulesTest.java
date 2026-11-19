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
package com.iluwatar.functionalcoreimperativeshell.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

/** The core is tested with plain values: no mocks, no clock, no storage. */
class LibraryRulesTest {

  private static final LocalDate DUE = LocalDate.of(2024, 3, 15);
  private static final Member ALICE = Member.newMember("alice");
  private static final Loan LOAN = new Loan("LOAN-1", "alice", "The Hobbit", DUE);

  @Test
  void lendSetsDueDateAfterLoanPeriod() {
    var loan = LibraryRules.lend("LOAN-7", ALICE, "Dune", LocalDate.of(2024, 3, 1));

    assertEquals(new Loan("LOAN-7", "alice", "Dune", DUE), loan);
  }

  @Test
  void lendRefusesMemberAtBorrowingLimit() {
    var indebted = new Member("bob", LibraryRules.BORROWING_LIMIT_CENTS);

    assertFalse(LibraryRules.canBorrow(indebted));
    assertThrows(
        IllegalStateException.class,
        () -> LibraryRules.lend("LOAN-1", indebted, "Dune", LocalDate.of(2024, 3, 1)));
  }

  @Test
  void canBorrowJustBelowLimit() {
    assertTrue(LibraryRules.canBorrow(new Member("bob", LibraryRules.BORROWING_LIMIT_CENTS - 1)));
  }

  @Test
  void fineGrowsPerDayAndIsCapped() {
    assertEquals(0, LibraryRules.fineFor(0));
    assertEquals(0, LibraryRules.fineFor(-3));
    assertEquals(75, LibraryRules.fineFor(3));
    assertEquals(LibraryRules.MAX_FINE_CENTS, LibraryRules.fineFor(20));
    assertEquals(LibraryRules.MAX_FINE_CENTS, LibraryRules.fineFor(365));
  }

  @Test
  void onTimeReturnCostsNothing() {
    var outcome = LibraryRules.settleReturn(ALICE, LOAN, DUE.minusDays(2));

    assertEquals(new ReturnOutcome(ALICE, 0, 0, false), outcome);
  }

  @Test
  void returnOnDueDateIsFreeAndOneDayLateIsFined() {
    assertEquals(
        new ReturnOutcome(ALICE, 0, 0, false), LibraryRules.settleReturn(ALICE, LOAN, DUE));

    var oneDayLate = LibraryRules.settleReturn(ALICE, LOAN, DUE.plusDays(1));

    assertEquals(1, oneDayLate.daysLate());
    assertEquals(LibraryRules.DAILY_FINE_CENTS, oneDayLate.fineCents());
  }

  @Test
  void lateReturnAddsFineToNewMemberAndLeavesOriginalUntouched() {
    var outcome = LibraryRules.settleReturn(ALICE, LOAN, DUE.plusDays(4));

    assertEquals(4, outcome.daysLate());
    assertEquals(100, outcome.fineCents());
    assertEquals(new Member("alice", 100), outcome.member());
    assertFalse(outcome.borrowingBlocked());
    assertEquals(0, ALICE.finesCents());
  }

  @Test
  void returnThatReachesLimitBlocksBorrowing() {
    var owing = new Member("alice", 600);

    var outcome = LibraryRules.settleReturn(owing, LOAN, DUE.plusDays(30));

    assertEquals(1_100, outcome.member().finesCents());
    assertTrue(outcome.borrowingBlocked());
  }

  @Test
  void returnRejectsLoanOfAnotherMember() {
    var bob = Member.newMember("bob");

    assertThrows(IllegalArgumentException.class, () -> LibraryRules.settleReturn(bob, LOAN, DUE));
  }

  @Test
  void memberRejectsInvalidState() {
    assertThrows(IllegalArgumentException.class, () -> new Member(" ", 0));
    assertThrows(IllegalArgumentException.class, () -> new Member("alice", -1));
  }
}
