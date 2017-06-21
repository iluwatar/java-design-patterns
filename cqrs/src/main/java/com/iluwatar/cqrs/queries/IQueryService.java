package com.iluwatar.cqrs.queries;

import java.util.List;

import com.iluwatar.cqrs.dto.AuthorDTO;
import com.iluwatar.cqrs.dto.BookDTO;

public interface IQueryService {

  public abstract AuthorDTO getAuthorByUsername(String username);

  public abstract Double getBookPrice(String title);

  public abstract List<BookDTO> getAuthorBooks(String username);

  public abstract long getAuthorBooksCount(String username);

  public abstract long getAuthorsCount();

}
