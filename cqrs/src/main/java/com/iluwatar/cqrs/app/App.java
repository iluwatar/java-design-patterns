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

package com.iluwatar.cqrs.app;

import com.iluwatar.cqrs.commandes.CommandServiceImpl;
import com.iluwatar.cqrs.constants.AppConstants;
import com.iluwatar.cqrs.queries.QueryServiceImpl;
import com.iluwatar.cqrs.util.HibernateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CQRS : Command Query Responsibility Segregation. A pattern used to separate query services from
 * commands or writes services. The pattern is very simple but it has many consequences. For
 * example, it can be used to tackle down a complex domain, or to use other architectures that were
 * hard to implement with the classical way.
 *
 * <p>This implementation is an example of managing books and authors in a library. The persistence
 * of books and authors is done according to the CQRS architecture. A command side that deals with a
 * data model to persist(insert,update,delete) objects to a database. And a query side that uses
 * native queries to get data from the database and return objects as DTOs (Data transfer Objects).
 */
public class App {
  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  /**
   * Program entry point.
   *
   * @param args command line args
   */
  public static void main(String[] args) {
    var commands = new CommandServiceImpl();

    // Create Authors and Books using CommandService
    commands.authorCreated(AppConstants.E_EVANS, "Eric Evans", "evans@email.com");
    commands.authorCreated(AppConstants.J_BLOCH, "Joshua Bloch", "jBloch@email.com");
    commands.authorCreated(AppConstants.M_FOWLER, "Martin Fowler", "mFowler@email.com");

    commands.bookAddedToAuthor("Domain-Driven Design", 60.08, AppConstants.E_EVANS);
    commands.bookAddedToAuthor("Effective Java", 40.54, AppConstants.J_BLOCH);
    commands.bookAddedToAuthor("Java Puzzlers", 39.99, AppConstants.J_BLOCH);
    commands.bookAddedToAuthor("Java Concurrency in Practice", 29.40, AppConstants.J_BLOCH);
    commands.bookAddedToAuthor("Patterns of Enterprise"
        + " Application Architecture", 54.01, AppConstants.M_FOWLER);
    commands.bookAddedToAuthor("Domain Specific Languages", 48.89, AppConstants.M_FOWLER);
    commands.authorNameUpdated(AppConstants.E_EVANS, "Eric J. Evans");

    var queries = new QueryServiceImpl();

    // Query the database using QueryService
    var nullAuthor = queries.getAuthorByUsername("username");
    var evans = queries.getAuthorByUsername(AppConstants.E_EVANS);
    var blochBooksCount = queries.getAuthorBooksCount(AppConstants.J_BLOCH);
    var authorsCount = queries.getAuthorsCount();
    var dddBook = queries.getBook("Domain-Driven Design");
    var blochBooks = queries.getAuthorBooks(AppConstants.J_BLOCH);

    LOGGER.info("Author username : {}", nullAuthor);
    LOGGER.info("Author evans : {}", evans);
    LOGGER.info("jBloch number of books : {}", blochBooksCount);
    LOGGER.info("Number of authors : {}", authorsCount);
    LOGGER.info("DDD book : {}", dddBook);
    LOGGER.info("jBloch books : {}", blochBooks);

    HibernateUtil.getSessionFactory().close();
  }

}
