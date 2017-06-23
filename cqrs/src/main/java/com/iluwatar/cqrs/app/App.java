package com.iluwatar.cqrs.app;

import java.math.BigInteger;
import java.util.List;

import com.iluwatar.cqrs.commandes.CommandServiceImpl;
import com.iluwatar.cqrs.commandes.ICommandService;
import com.iluwatar.cqrs.dto.Author;
import com.iluwatar.cqrs.dto.Book;
import com.iluwatar.cqrs.queries.IQueryService;
import com.iluwatar.cqrs.queries.QueryServiceImpl;
import com.iluwatar.cqrs.util.HibernateUtil;

/**
 * This is the entry of the application
 *
 */
public class App {
  /**
   * Program entry point
   * 
   * @param args
   *          command line args
   */
  public static void main(String[] args) {
    ICommandService commands = new CommandServiceImpl();

    // Create Authors and Books using CommandService
    commands.authorCreated("eEvans", "Eric Evans", "eEvans@email.com");
    commands.authorCreated("jBloch", "Joshua Bloch", "jBloch@email.com");
    commands.authorCreated("mFowler", "Martin Fowler", "mFowler@email.com");

    commands.bookAddedToAuthor("Domain-Driven Design", 60.08, "eEvans");
    commands.bookAddedToAuthor("Effective Java", 40.54, "jBloch");
    commands.bookAddedToAuthor("Java Puzzlers", 39.99, "jBloch");
    commands.bookAddedToAuthor("Java Concurrency in Practice", 29.40, "jBloch");
    commands.bookAddedToAuthor("Patterns of Enterprise Application Architecture", 54.01, "mFowler");
    commands.bookAddedToAuthor("Domain Specific Languages", 48.89, "mFowler");
    commands.authorNameUpdated("eEvans", "Eric J. Evans");

    IQueryService queries = new QueryServiceImpl();

    // Query the database using QueryService
    Author nullAuthor = queries.getAuthorByUsername("username");
    Author eEvans = queries.getAuthorByUsername("eEvans");
    BigInteger jBlochBooksCount = queries.getAuthorBooksCount("jBloch");
    BigInteger authorsCount = queries.getAuthorsCount();
    Book dddBook = queries.getBook("Domain-Driven Design");
    List<Book> jBlochBooks = queries.getAuthorBooks("jBloch");

    HibernateUtil.getSessionFactory().close();
  }

}
