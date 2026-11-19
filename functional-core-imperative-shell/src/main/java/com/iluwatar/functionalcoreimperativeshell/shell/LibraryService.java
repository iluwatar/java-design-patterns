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

import com.iluwatar.functionalcoreimperativeshell.core.LibraryRules;
import com.iluwatar.functionalcoreimperativeshell.core.Loan;
import com.iluwatar.functionalcoreimperativeshell.core.Member;
import com.iluwatar.functionalcoreimperativeshell.core.ReturnOutcome;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * The imperative shell. Each operation gathers what the core needs (it loads records and reads the
 * clock), asks {@link LibraryRules} for a decision, then carries the decision out: it saves the new
 * state and logs the result. The shell itself makes no business decisions.
 */
@Slf4j
@RequiredArgsConstructor
public class LibraryService {

  private final LibraryStore store;
  private final Clock clock;

  /** Register a new member who owes nothing. */
  public Member register(String name) {
    var member = Member.newMember(name);
    store.saveMember(member);
    LOGGER.info("Registered {}", name);
    return member;
  }

  /**
   * Lend a book to a member.
   *
   * @return the new loan, or empty when the rules refuse it
   */
  public Optional<Loan> borrow(String memberName, String title) {
    var member = loadMember(memberName);
    if (!LibraryRules.canBorrow(member)) {
      LOGGER.info("{} may not borrow '{}': owes {} cents", memberName, title, member.finesCents());
      return Optional.empty();
    }
    var loan = LibraryRules.lend(store.nextLoanId(), member, title, today());
    store.saveLoan(loan);
    LOGGER.info("{} borrowed '{}', due {}", memberName, title, loan.dueDate());
    return Optional.of(loan);
  }

  /** Take a book back, charge any late fine and close the loan. */
  public ReturnOutcome returnBook(String loanId) {
    var loan =
        store
            .findLoan(loanId)
            .orElseThrow(() -> new IllegalArgumentException("No open loan " + loanId));
    var outcome = LibraryRules.settleReturn(loadMember(loan.memberName()), loan, today());
    store.saveMember(outcome.member());
    store.removeLoan(loanId);
    LOGGER.info(
        "{} returned '{}' {} days late, fined {} cents, now owes {} cents{}",
        loan.memberName(),
        loan.title(),
        outcome.daysLate(),
        outcome.fineCents(),
        outcome.member().finesCents(),
        outcome.borrowingBlocked() ? " and may not borrow" : "");
    return outcome;
  }

  private Member loadMember(String name) {
    return store
        .findMember(name)
        .orElseThrow(() -> new IllegalArgumentException("Unknown member " + name));
  }

  private LocalDate today() {
    return LocalDate.now(clock);
  }
}
