package com.biblioteca.big.service;

import com.biblioteca.big.exception.BookAlreadyExistsException;
import com.biblioteca.big.exception.BookNotFoundException;
import com.biblioteca.big.model.Book;
import com.biblioteca.big.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    //POST BOOK
    public void insertBook (@RequestBody Book book) throws BookAlreadyExistsException {
        Book existsBook = bookRepository.findByTitleAndAuthor(book.getTitle(), book.getAuthor());
        if (existsBook != null){
            throw new BookAlreadyExistsException("El libro ya existe");
        }
        bookRepository.save(book);
    }

    //PUT BOOK BY ID
    public void updateExistsBook (@RequestBody Book book,
                                  @PathVariable Integer id)
            throws BookNotFoundException{
        Optional<Book> bookOptional = bookRepository.findById(id);
        if(!bookOptional.isPresent()) {
            throw new BookNotFoundException("El libro no existe");
        }
        book.setId(id);
        bookRepository.save(book);
        ResponseEntity.noContent().build();
    }

    //GET BOOK BY STATUS
    public List<Book> getAllBooksByStatus(@PathVariable("status") String status){
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

    //GET BOOK BY ID
    public Book getExistsBookById(@PathVariable Integer id) throws BookNotFoundException {
        Optional<Book> book = bookRepository.findById(id);
        if(!book.isPresent()){
            throw new BookNotFoundException("No se encontró el ID: " + id);
        }
        return book.get();
    }

    //GET ALL BOOKS
    public List<Book> getAllExistsBooks(){
        return bookRepository.findAll(Sort.by("title").ascending());
    }

    //DELETE BOOK BY ID
    public void deleteExistsBookById(@PathVariable Integer id) throws BookNotFoundException {
        Optional<Book> book = bookRepository.findById(id);
        if(!book.isPresent()){
            throw new BookNotFoundException("No se encontró el ID: " + id);
        }
        bookRepository.deleteById(id);
    }
}
