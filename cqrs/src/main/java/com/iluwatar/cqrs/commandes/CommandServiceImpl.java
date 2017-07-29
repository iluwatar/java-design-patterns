/**
 * The MIT License
 * Copyright (c) 2014 Ilkka Seppälä
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
package com.iluwatar.cqrs.commandes;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.iluwatar.cqrs.domain.model.Author;
import com.iluwatar.cqrs.domain.model.Book;
import com.iluwatar.cqrs.util.HibernateUtil;

/**
 * This class is an implementation of {@link ICommandService} interface. It uses Hibernate as an api for persistence.
 *
 */
public class CommandServiceImpl implements ICommandService {

  private SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

  private Author getAuthorByUsername(String username) {
    Author author = null;
    try (Session session = sessionFactory.openSession()) {
      Query query = session.createQuery("from Author where username=:username");
      query.setParameter("username", username);
      author = (Author) query.uniqueResult();
    }
    if (author == null) {
      HibernateUtil.getSessionFactory().close();
      throw new NullPointerException("Author " + username + " doesn't exist!");
    }
    return author;
  }

  private Book getBookByTitle(String title) {
    Book book = null;
    try (Session session = sessionFactory.openSession()) {
      Query query = session.createQuery("from Book where title=:title");
      query.setParameter("title", title);
      book = (Book) query.uniqueResult();
    }
    if (book == null) {
      HibernateUtil.getSessionFactory().close();
      throw new NullPointerException("Book " + title + " doesn't exist!");
    }
    return book;
  }

  @Override
  public void authorCreated(String username, String name, String email) {
    Author author = new Author(username, name, email);
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      session.save(author);
      session.getTransaction().commit();
    }
  }

  @Override
  public void bookAddedToAuthor(String title, double price, String username) {
    Author author = getAuthorByUsername(username);
    Book book = new Book(title, price, author);
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      session.save(book);
      session.getTransaction().commit();
    }
  }

  @Override
  public void authorNameUpdated(String username, String name) {
    Author author = getAuthorByUsername(username);
    author.setName(name);
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      session.update(author);
      session.getTransaction().commit();
    }
  }

  @Override
  public void authorUsernameUpdated(String oldUsername, String newUsername) {
    Author author = getAuthorByUsername(oldUsername);
    author.setUsername(newUsername);
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      session.update(author);
      session.getTransaction().commit();
    }
  }

  @Override
  public void authorEmailUpdated(String username, String email) {
    Author author = getAuthorByUsername(username);
    author.setEmail(email);
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      session.update(author);
      session.getTransaction().commit();
    }
  }

  @Override
  public void bookTitleUpdated(String oldTitle, String newTitle) {
    Book book = getBookByTitle(oldTitle);
    book.setTitle(newTitle);
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      session.update(book);
      session.getTransaction().commit();
    }
  }

  @Override
  public void bookPriceUpdated(String title, double price) {
    Book book = getBookByTitle(title);
    book.setPrice(price);
    try (Session session = sessionFactory.openSession()) {
      session.beginTransaction();
      session.update(book);
      session.getTransaction().commit();
    }
  }

}
