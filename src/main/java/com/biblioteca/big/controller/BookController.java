package com.biblioteca.big.controller;

import com.biblioteca.big.exception.BookAlreadyExistsException;
import com.biblioteca.big.exception.BookNotFoundException;
import com.biblioteca.big.model.Book;
import com.biblioteca.big.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequestMapping("/books")
@RestController
public class BookController {
    @Autowired private BookService bookService;

    //POST BOOK
    @PostMapping("/create")
    public ResponseEntity<Void> createBook(@RequestBody Book book) throws BookAlreadyExistsException {
        bookService.createBook(book);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //PUT BOOK BY ID
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateBook(@RequestBody Book book,
                                           @PathVariable("id") Long id) throws BookNotFoundException {
        bookService.updateBook(book, id);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    //GET ALL BOOKS
    @GetMapping("/getAll")
    public List<Book> getAllBooks(){
        return bookService.getAllBooks();
    }

    //GET BOOK BY ID
    @GetMapping("/getById/{id}")
    public Book getBookById(@PathVariable("id") Long id) throws BookNotFoundException {
        return bookService.getBookById(id);
    }

    //GET BOOK BY STATUS
    @GetMapping("/getByStatus/{status}")
    public List<Book> getBooksByStatus(@PathVariable("status") String status) {
        return bookService.getBooksByStatus(status);
    }

    //DELETE BOOK BY ID
    @DeleteMapping("/delete/{id}")
    public void deleteBookById(@PathVariable("id") Long id) throws BookNotFoundException {
        bookService.deleteBookById(id);
    }
}

