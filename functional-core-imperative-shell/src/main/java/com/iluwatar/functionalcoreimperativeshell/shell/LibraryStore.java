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

import com.iluwatar.functionalcoreimperativeshell.core.Loan;
import com.iluwatar.functionalcoreimperativeshell.core.Member;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Persistence for members and open loans. It stands in for a database, so it belongs to the
 * imperative shell: it holds mutable state that outlives any single call.
 */
public class LibraryStore {

  private final Map<String, Member> members = new HashMap<>();
  private final Map<String, Loan> loans = new HashMap<>();
  private int loanSequence = 0;

  /** Find a member by name. */
  public Optional<Member> findMember(String name) {
    return Optional.ofNullable(members.get(name));
  }

  /** Insert or replace a member. */
  public void saveMember(Member member) {
    members.put(member.name(), member);
  }

  /** Hand out the next unused loan id, like a database sequence. */
  public String nextLoanId() {
    return "LOAN-" + ++loanSequence;
  }

  /** Find an open loan by id. */
  public Optional<Loan> findLoan(String id) {
    return Optional.ofNullable(loans.get(id));
  }

  /** Insert or replace an open loan. */
  public void saveLoan(Loan loan) {
    loans.put(loan.id(), loan);
  }

  /** Close a loan. */
  public void removeLoan(String id) {
    loans.remove(id);
  }

  /** The number of loans still open. */
  public int openLoanCount() {
    return loans.size();
  }
}
