package com.iluwater.fieild.services;
import com.iluwater.fieild.model.Book;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;

@Repository
public class BookRepository {
  private final EntityManager entityManager;

  public BookRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  public Book createBook(String title, String author) {
    Book book = new Book(title, author);
    entityManager.persist(book);
    return book;
  }
  public Book getBookById(Long id) {
    return entityManager.find(Book.class, id);
  }
}