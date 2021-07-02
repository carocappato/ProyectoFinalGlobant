package com.biblioteca.big.service;

import com.biblioteca.big.exception.BookAlreadyExistsException;
import com.biblioteca.big.exception.BookNotFoundException;
import com.biblioteca.big.model.Book;
import com.biblioteca.big.repository.BookRepository;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class BookService {
    @Autowired private BookRepository bookRepository;

    //POST BOOK
    public void createBook(@RequestBody Book book) throws BookAlreadyExistsException {
        Book existingBook = bookRepository.findByTitleAndAuthor(book.getTitle(), book.getAuthor());

        if (existingBook != null){
            throw new BookAlreadyExistsException("El libro ya existe");
        }

        bookRepository.save(book);
    }

    //PUT BOOK BY ID
    public void updateBook (@RequestBody Book book, @PathVariable("id") Long id) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(id);

        if(bookOptional.isEmpty()) {
            throw new BookNotFoundException("El libro no existe");
        }

        book.setId(id);
        bookRepository.save(book);
    }

    //GET BOOK BY STATUS
    public List<Book> getBooksByStatus(@PathVariable("status") String status) {
        List<Book> books = new ArrayList<>();

        if(status.equals("Disponible") || status.equals("DISPONIBLE") || status.equals("disponible")) {
            books = bookRepository.findByStatus("Disponible", Sort.by("title").ascending());
            return books;
        } else if (status.equals("Reservado") || status.equals("RESERVADO") || status.equals("reservado")) {
            books = bookRepository.findByStatus("Reservado", Sort.by("title").ascending());
            return books;
        }

        return books;
    }

    //GET BOOK BY ID
    public Book getBookById(@PathVariable("id") Long id) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(id);

        if(bookOptional.isEmpty()) {
            throw new BookNotFoundException("No se encontró el ID: " + id);
        }

        return bookOptional.get();
    }

    //GET ALL BOOKS
    public List<Book> getAllBooks() {
        return bookRepository.findAll(Sort.by("title").ascending());
    }

    //DELETE BOOK BY ID
    public void deleteBookById(@PathVariable("id") Long id) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(id);

        if(bookOptional.isEmpty()){
            throw new BookNotFoundException("No se encontró el ID: " + id);
        }

        bookRepository.deleteById(id);
    }
}
