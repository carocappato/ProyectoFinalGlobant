package com.biblioteca.big.controller;

import com.biblioteca.big.exception.BookAlreadyExistsException;
import com.biblioteca.big.exception.BookNotFoundException;
import com.biblioteca.big.model.Book;
import com.biblioteca.big.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/books")
@RestController
public class BookController {
    @Autowired
    private BookService bookService;

    //POST BOOK
    @PostMapping
    public ResponseEntity<Book> newBook(@RequestBody Book book) throws BookAlreadyExistsException {
        bookService.insertBook(book);

        return ResponseEntity.status(201).build();
    }

    //PUT BOOK BY ID
    @PutMapping("/{id}")
    public void updateBook (@RequestBody Book book,
                            @PathVariable("id") Long id) throws BookNotFoundException {
        bookService.updateExistsBook(book, id);
    }

    //GET ALL BOOKS
    @GetMapping
    public List<Book> getAllBooks(){
        return bookService.getAllExistsBooks();
    }

    //GET BOOK BY ID
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable("id") Long id) throws BookNotFoundException {
        return bookService.getExistsBookById(id);
    }

    //GET BOOK BY STATUS
    @GetMapping("/status/{status}")
    public List<Book> getBooksByStatus(@PathVariable("status") String status) {
        return bookService.getAllBooksByStatus(status);
    }

    //DELETE BOOK BY ID
    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable("id") Long id) throws BookNotFoundException {
        bookService.deleteExistsBookById(id);
    }
}

