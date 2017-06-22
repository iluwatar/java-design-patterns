package com.iluwatar.cqrs.queries;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;

import com.iluwatar.cqrs.dto.Author;
import com.iluwatar.cqrs.dto.Book;
import com.iluwatar.cqrs.util.HibernateUtil;

/**
 * This class is an implementation of {@link IQueryService}. It uses Hibernate native queries to return DTOs from the
 * database.
 *
 */
public class QueryServiceImpl implements IQueryService {

  private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

  @Override
  public Author getAuthorByUsername(String username) {
    Session session = sessionFactory.openSession();
    SQLQuery sqlQuery = session
        .createSQLQuery("SELECT a.username as \"username\", a.name as \"name\", a.email as \"email\""
            + "FROM Author a where a.username=:username");
    sqlQuery.setParameter("username", username);
    Author authorDTo = (Author) sqlQuery.setResultTransformer(Transformers.aliasToBean(Author.class)).uniqueResult();
    session.close();
    return authorDTo;
  }

  @Override
  public Book getBook(String title) {
    Session session = sessionFactory.openSession();
    SQLQuery sqlQuery = session
        .createSQLQuery("SELECT b.title as \"title\", b.price as \"price\"" + " FROM Book b where b.title=:title");
    sqlQuery.setParameter("title", title);
    Book bookDTo = (Book) sqlQuery.setResultTransformer(Transformers.aliasToBean(Book.class)).uniqueResult();
    session.close();
    return bookDTo;
  }

  @Override
  public List<Book> getAuthorBooks(String username) {
    Session session = sessionFactory.openSession();
    SQLQuery sqlQuery = session.createSQLQuery("SELECT b.title as \"title\", b.price as \"price\""
        + " FROM Author a , Book b where b.author_id = a.id and a.username=:username");
    sqlQuery.setParameter("username", username);
    List<Book> bookDTos = sqlQuery.setResultTransformer(Transformers.aliasToBean(Book.class)).list();
    session.close();
    return bookDTos;
  }

  @Override
  public BigInteger getAuthorBooksCount(String username) {
    Session session = sessionFactory.openSession();
    SQLQuery sqlQuery = session.createSQLQuery(
        "SELECT count(b.title)" + " FROM  Book b, Author a where b.author_id = a.id and a.username=:username");
    sqlQuery.setParameter("username", username);
    BigInteger bookcount = (BigInteger) sqlQuery.uniqueResult();
    session.close();
    return bookcount;
  }

  @Override
  public BigInteger getAuthorsCount() {
    Session session = sessionFactory.openSession();
    SQLQuery sqlQuery = session.createSQLQuery("SELECT count(id) from Author");
    BigInteger authorcount = (BigInteger) sqlQuery.uniqueResult();
    session.close();
    return authorcount;
  }

}
