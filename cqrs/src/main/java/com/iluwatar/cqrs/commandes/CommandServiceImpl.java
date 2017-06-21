package com.iluwatar.cqrs.commandes;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.iluwatar.cqrs.domain.model.Author;
import com.iluwatar.cqrs.domain.model.Book;
import com.iluwatar.cqrs.util.HibernateUtil;

public class CommandServiceImpl implements ICommandService {

  private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

  private Author getAuthorByUsername(String username) {
    Session session = sessionFactory.openSession();
    Query query = session.createQuery("from Author where username=:username");
    query.setParameter("username", username);
    Author author = (Author) query.uniqueResult();
    session.close();
    return author;
  }

  private Book getBookByTitle(String title) {
    Session session = sessionFactory.openSession();
    Query query = session.createQuery("from Book where title=:title");
    query.setParameter("title", title);
    Book book = (Book) query.uniqueResult();
    session.close();
    return book;
  }

  @Override
  public void authorCreated(String username, String name, String email) {
    Author author = new Author(username, name, email);
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.save(author);
    session.getTransaction().commit();
    session.close();
  }

  @Override
  public void bookAddedToAuthor(String title, double price, String username) {
    Author author = getAuthorByUsername(username);
    Book book = new Book(title, price, author);
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.save(book);
    session.getTransaction().commit();
    session.close();
  }

  @Override
  public void authorNameUpdated(String username, String name) {
    Author author = getAuthorByUsername(username);
    author.setName(name);
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.update(author);
    session.getTransaction().commit();
    session.close();
  }

  @Override
  public void authorUsernameUpdated(String oldUsername, String newUsername) {
    Author author = getAuthorByUsername(oldUsername);
    author.setUsername(newUsername);
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.update(author);
    session.getTransaction().commit();
    session.close();

  }

  @Override
  public void authorEmailUpdated(String username, String email) {
    Author author = getAuthorByUsername(username);
    author.setEmail(email);
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.update(author);
    session.getTransaction().commit();
    session.close();

  }

  @Override
  public void bookTitleUpdated(String oldTitle, String newTitle) {
    Book book = getBookByTitle(oldTitle);
    book.setTitle(newTitle);
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.update(book);
    session.getTransaction().commit();
    session.close();
  }

  @Override
  public void bookPriceUpdated(String title, double price) {
    Book book = getBookByTitle(title);
    book.setPrice(price);
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.update(book);
    session.getTransaction().commit();
    session.close();
  }

}
