package com.iluwater.fieild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class BookRepository extends JpaRepository<Book, Long> {
  List<Book> BookList =new ArrayList<>();

  public Book createBook(String title,String author) {
    Book book = new Book(title, author);
    BookList.add(book);
    return book;
  }
  public Book getBookById(Long id) {
    for (Book book: BookList )
    {
      if(Objects.equals(book.getId(), id))
        return book;
    }
    return null;
  }
}