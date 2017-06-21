package com.iluwatar.cqrs.queries;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;

import com.iluwatar.cqrs.dto.AuthorDTO;
import com.iluwatar.cqrs.dto.BookDTO;
import com.iluwatar.cqrs.util.HibernateUtil;

public class QueryServiceImpl implements IQueryService {

  private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

  @Override
  public AuthorDTO getAuthorByUsername(String username) {
    Session session = sessionFactory.openSession();
    SQLQuery sqlQuery = session
        .createSQLQuery("SELECT a.username as \"username\", a.name as \"name\", a.email as \"email\""
            + "FROM Author a where a.username=:username");
    sqlQuery.setParameter("username", username);
    AuthorDTO authorDTO = (AuthorDTO) sqlQuery.setResultTransformer(Transformers.aliasToBean(AuthorDTO.class))
        .uniqueResult();
    session.close();
    return authorDTO;
  }

  @Override
  public BookDTO getBook(String title) {
    Session session = sessionFactory.openSession();
    SQLQuery sqlQuery = session
        .createSQLQuery("SELECT b.title as \"title\", b.price as \"price\"" + " FROM Book b where b.title=:title");
    sqlQuery.setParameter("title", title);
    BookDTO bookDTO = (BookDTO) sqlQuery.setResultTransformer(Transformers.aliasToBean(BookDTO.class)).uniqueResult();
    session.close();
    return bookDTO;
  }

  @Override
  public List<BookDTO> getAuthorBooks(String username) {
    Session session = sessionFactory.openSession();
    SQLQuery sqlQuery = session.createSQLQuery("SELECT b.title as \"title\", b.price as \"price\""
        + " FROM Author a , Book b where b.author_id = a.id and a.username=:username");
    sqlQuery.setParameter("username", username);
    List<BookDTO> bookDTOs = sqlQuery.setResultTransformer(Transformers.aliasToBean(BookDTO.class)).list();
    session.close();
    return bookDTOs;
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
