package com.biblioteca.big.controller;

import com.biblioteca.big.exception.BookNotFoundException;
import com.biblioteca.big.model.Book;
import com.biblioteca.big.repository.BookRepository;
import com.biblioteca.big.service.BookService;
import com.sun.source.tree.LambdaExpressionTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequestMapping("/books")
@RestController
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks(){
        return bookRepository.findAll(Sort.by("title").ascending());
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) throws BookNotFoundException {
        Optional<Book> book = bookRepository.findById(id);

        if(!book.isPresent()){
            throw new BookNotFoundException("No se encontró el ID: " + id);
        }

        return book.get();
    }


    @GetMapping("/status/{status}")
    public List<Book> getBooksByStatus(@PathVariable("status") String status){
        List<Book> books = new ArrayList<>();

        if(status.equals("DISPONIBLE")){
            books = bookRepository.findByStatus("DISPONIBLE", Sort.by("title").ascending());
            return books;
        }
        else if (status.equals("RESERVADO")){
            books = bookRepository.findByStatus("RESERVADO", Sort.by("title").ascending());
            return books;
        }
        return books;
    }

    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable Long id) throws BookNotFoundException{
        Optional<Book> book = bookRepository.findById(id);

        if(!book.isPresent()){
            throw new BookNotFoundException("No se encontró el ID: " + id);
        }

        bookRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateBook (@RequestBody Book book, @PathVariable Long id){
        Optional<Book> bookOptional = bookRepository.findById(id);

        if(!bookOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        book.setId(id);
        bookRepository.save(book);

        return ResponseEntity.noContent().build();
    }

    //TODO: POST Y VALIDACION

}
