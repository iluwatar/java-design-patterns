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
package com.iluwatar.functionalcoreimperativeshell;

import com.iluwatar.functionalcoreimperativeshell.core.LibraryRules;
import com.iluwatar.functionalcoreimperativeshell.shell.LibraryService;
import com.iluwatar.functionalcoreimperativeshell.shell.LibraryStore;
import java.time.Clock;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;

/**
 * Functional Core, Imperative Shell splits a program into two layers. The functional core ({@link
 * LibraryRules} and its immutable records) holds every business rule as a pure function: no I/O, no
 * clock, no logging. The imperative shell ({@link LibraryService} and {@link LibraryStore}) does
 * all of that and calls the core for each decision.
 *
 * <p>This example runs a small library. A member borrows two books, keeps them too long, and is
 * fined when returning them; once the fines pass the limit, the core refuses the next loan. The
 * passing of time is simulated by giving the shell a clock set five weeks ahead, so the core never
 * learns where the date came from.
 */
@Slf4j
public class App {

  /** Program entry point. */
  public static void main(String[] args) {
    var store = new LibraryStore();
    var clock = Clock.systemDefaultZone();
    var library = new LibraryService(store, clock);

    library.register("alice");
    var novel = library.borrow("alice", "The Hobbit").orElseThrow();
    var atlas = library.borrow("alice", "World Atlas").orElseThrow();

    var fiveWeeksLater = new LibraryService(store, Clock.offset(clock, Duration.ofDays(35)));
    fiveWeeksLater.returnBook(novel.id());
    fiveWeeksLater.returnBook(atlas.id());
    fiveWeeksLater.borrow("alice", "Dune");

    LOGGER.info("Open loans left: {}", store.openLoanCount());
  }
}
