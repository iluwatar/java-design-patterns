package com.iluwater.fieild.Services;
import com.iluwater.fieild.Model.Book;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class BookRepository {
  private EntityManager entityManager;

  public Book createBook(String title,String author) {
    Book book = new Book(title, author);
    entityManager.persist(book);
    return book;
  }
  public Book getBookById(Long id) {
    return entityManager.find(Book.class, id);
  }
}