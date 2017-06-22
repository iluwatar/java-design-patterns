package com.iluwatar.cqrs.queries;

import java.math.BigInteger;
import java.util.List;

import com.iluwatar.cqrs.dto.Author;
import com.iluwatar.cqrs.dto.Book;

/**
 * 
 * This interface represents the query methods of the CQRS pattern
 *
 */
public interface IQueryService {

  public abstract Author getAuthorByUsername(String username);

  public abstract Book getBook(String title);

  public abstract List<Book> getAuthorBooks(String username);

  public abstract BigInteger getAuthorBooksCount(String username);

  public abstract BigInteger getAuthorsCount();

}
