/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
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

package com.iluwatar.cqrs.queries;

import com.iluwatar.cqrs.constants.AppConstants;
import com.iluwatar.cqrs.dto.Author;
import com.iluwatar.cqrs.dto.Book;
import com.iluwatar.cqrs.util.HibernateUtil;
import java.math.BigInteger;
import java.util.List;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;

/**
 * This class is an implementation of {@link IQueryService}. It uses Hibernate native queries to
 * return DTOs from the database.
 */
public class QueryServiceImpl implements IQueryService {

  private final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

  @Override
  public Author getAuthorByUsername(String username) {
    Author authorDTo;
    try (var session = sessionFactory.openSession()) {
      var sqlQuery = session.createSQLQuery("SELECT a.username as \"username\","
          + " a.name as \"name\", a.email as \"email\""
          + "FROM Author a where a.username=:username");
      sqlQuery.setParameter(AppConstants.USER_NAME, username);
      authorDTo = (Author) sqlQuery.setResultTransformer(Transformers.aliasToBean(Author.class))
          .uniqueResult();
    }
    return authorDTo;
  }

  @Override
  public Book getBook(String title) {
    Book bookDTo;
    try (var session = sessionFactory.openSession()) {
      var sqlQuery = session.createSQLQuery("SELECT b.title as \"title\","
          + " b.price as \"price\"" + " FROM Book b where b.title=:title");
      sqlQuery.setParameter("title", title);
      bookDTo =
          (Book) sqlQuery.setResultTransformer(Transformers.aliasToBean(Book.class)).uniqueResult();
    }
    return bookDTo;
  }

  @Override
  public List<Book> getAuthorBooks(String username) {
    List<Book> bookDTos;
    try (var session = sessionFactory.openSession()) {
      var sqlQuery = session.createSQLQuery("SELECT b.title as \"title\", b.price as \"price\""
          + " FROM Author a , Book b where b.author_id = a.id and a.username=:username");
      sqlQuery.setParameter(AppConstants.USER_NAME, username);
      bookDTos = sqlQuery.setResultTransformer(Transformers.aliasToBean(Book.class)).list();
    }
    return bookDTos;
  }

  @Override
  public BigInteger getAuthorBooksCount(String username) {
    BigInteger bookcount;
    try (var session = sessionFactory.openSession()) {
      var sqlQuery = session.createSQLQuery(
          "SELECT count(b.title)" + " FROM  Book b, Author a"
              + " where b.author_id = a.id and a.username=:username");
      sqlQuery.setParameter(AppConstants.USER_NAME, username);
      bookcount = (BigInteger) sqlQuery.uniqueResult();
    }
    return bookcount;
  }

  @Override
  public BigInteger getAuthorsCount() {
    BigInteger authorcount;
    try (var session = sessionFactory.openSession()) {
      var sqlQuery = session.createSQLQuery("SELECT count(id) from Author");
      authorcount = (BigInteger) sqlQuery.uniqueResult();
    }
    return authorcount;
  }

}
