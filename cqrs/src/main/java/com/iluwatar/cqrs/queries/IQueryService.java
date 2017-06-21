package com.iluwatar.cqrs.queries;

import java.math.BigInteger;
import java.util.List;

import com.iluwatar.cqrs.dto.AuthorDTO;
import com.iluwatar.cqrs.dto.BookDTO;

public interface IQueryService {

  public abstract AuthorDTO getAuthorByUsername(String username);

  public abstract BookDTO getBook(String title);

  public abstract List<BookDTO> getAuthorBooks(String username);

  public abstract BigInteger getAuthorBooksCount(String username);

  public abstract BigInteger getAuthorsCount();

}
