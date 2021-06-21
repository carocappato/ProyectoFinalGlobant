package com.biblioteca.big.controller;


import com.biblioteca.big.model.Book;
import com.biblioteca.big.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {
    @Autowired
    private BookRepository bookRepository;


    @GetMapping("/books")
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }
}
