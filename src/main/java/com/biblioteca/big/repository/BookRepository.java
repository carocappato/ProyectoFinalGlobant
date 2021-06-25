package com.biblioteca.big.repository;

import com.biblioteca.big.model.Book;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>{

    @Query("SELECT b FROM Book b WHERE b.bookStatus = ?1" )
    List<Book> findByStatus(String bookStatus, Sort sort);

    @Query ("SELECT b FROM Book b WHERE b.title = ?1 AND b.author = ?2")
    Book findByTitleAndAuthor(String title, String author);
}
