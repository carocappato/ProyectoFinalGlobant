package com.biblioteca.big.service;

import com.biblioteca.big.model.Book;
import com.biblioteca.big.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private Book book;

    /*
    public List<Book> getByStatus(Sort title){
        if (book.getStatus()=="DISPONIBLE"){
            return bookRepository.findAll();
        }
        return null;
    }
    */
}
