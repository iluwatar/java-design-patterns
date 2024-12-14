package com.iluwater.fieild.services;
import com.iluwater.fieild.model.Book;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;
/**
 * Book Repo.
 */
@Repository
public class BookRepository {
  private final EntityManager entityManager;
  /**
   * BookRepository constructor.
   */
  public BookRepository(EntityManager entityManager) {
    this.entityManager = entityManager;
  }
  /**
   * create a new book.
   */
  public Book createBook(String title, String author) {
    Book book = new Book(title, author);
    entityManager.persist(book);
    return book;
  }
  /**
   *  ID Getter.
   */
  public Book getBookById(Long id) {
    return entityManager.find(Book.class, id);
  }
}