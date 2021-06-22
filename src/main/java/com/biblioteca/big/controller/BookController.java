package com.biblioteca.big.controller;

import com.biblioteca.big.model.Book;
import com.biblioteca.big.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {
    @Autowired
    private BookRepository bookRepository;


    @GetMapping("/books")
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable Long id){
        Optional<Book> book = bookRepository.findById(id);

        //TODO Manejar excepción de libro no encontrado

        return book.get();
    }

    @DeleteMapping("/books/{id}")
    public void deleteBookById(@PathVariable Long id){
        bookRepository.deleteById(id);
    }

    @PutMapping("books/{id}")
    public ResponseEntity<Object> updateBook (@RequestBody Book book, @PathVariable Long id){
        Optional<Book> bookOptional = bookRepository.findById(id);

        if(!bookOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        book.setId(id);
        bookRepository.save(book);

        return ResponseEntity.noContent().build();
    }


}
