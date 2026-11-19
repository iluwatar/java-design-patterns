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

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * The functional core: the library's business rules as pure functions. Every method computes its
 * result only from its arguments. None of them reads the clock, touches storage or logs; the
 * current date is passed in by the caller.
 */
public final class LibraryRules {

  /** How long a member may keep a book. */
  public static final int LOAN_PERIOD_DAYS = 14;

  /** The fine for each day a book is late, in cents. */
  public static final long DAILY_FINE_CENTS = 25;

  /** The highest fine charged for a single late book, in cents. */
  public static final long MAX_FINE_CENTS = 500;

  /** Members owing at least this much, in cents, may not borrow. */
  public static final long BORROWING_LIMIT_CENTS = 1_000;

  private LibraryRules() {}

  /** Whether the member's unpaid fines still allow them to borrow. */
  public static boolean canBorrow(Member member) {
    return member.finesCents() < BORROWING_LIMIT_CENTS;
  }

  /**
   * Lend a book to a member.
   *
   * @param loanId the id the new loan gets
   * @param member the borrowing member
   * @param title the title of the book
   * @param today the date the book is lent
   * @return the new loan, due {@link #LOAN_PERIOD_DAYS} days after {@code today}
   * @throws IllegalStateException if the member owes too much to borrow
   */
  public static Loan lend(String loanId, Member member, String title, LocalDate today) {
    if (!canBorrow(member)) {
      throw new IllegalStateException(member.name() + " owes too much to borrow");
    }
    return new Loan(loanId, member.name(), title, today.plusDays(LOAN_PERIOD_DAYS));
  }

  /** The fine for a book returned the given number of days late, capped at the maximum. */
  public static long fineFor(long daysLate) {
    return Math.min(Math.max(daysLate, 0) * DAILY_FINE_CENTS, MAX_FINE_CENTS);
  }

  /**
   * Settle the return of a book.
   *
   * @param member the member returning the book
   * @param loan the loan being closed
   * @param returnedOn the date the book came back
   * @return the member's new balance and what was charged
   * @throws IllegalArgumentException if the loan belongs to another member
   */
  public static ReturnOutcome settleReturn(Member member, Loan loan, LocalDate returnedOn) {
    if (!loan.memberName().equals(member.name())) {
      throw new IllegalArgumentException(
          "Loan " + loan.id() + " does not belong to " + member.name());
    }
    var daysLate = Math.max(ChronoUnit.DAYS.between(loan.dueDate(), returnedOn), 0);
    var fine = fineFor(daysLate);
    var updated = member.withFineAdded(fine);
    return new ReturnOutcome(updated, daysLate, fine, !canBorrow(updated));
  }
}
