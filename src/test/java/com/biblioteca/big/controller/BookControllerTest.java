package com.biblioteca.big.controller;

import com.biblioteca.big.exception.BookAlreadyExistsException;
import com.biblioteca.big.exception.BookNotFoundException;
import com.biblioteca.big.model.Book;
import com.biblioteca.big.service.BookService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @InjectMocks private BookController bookController; //Clase que quiero testear
    @Mock private BookService bookService; //Dependencia que quiero mockear
    @Captor private ArgumentCaptor<Book> bookArgumentCaptor;
    @Captor private ArgumentCaptor<Long> longArgumentCaptor;

    @Test
    @DisplayName("It should create a book")
    public void createBookTest() throws BookAlreadyExistsException {
        Book bookOne = new Book(1L,"First Book", "First Author", 2000, "Disponible");

        doNothing().when(bookService).createBook(any());
        ResponseEntity<Void> responseEntity = bookController.createBook(bookOne);

        verify(bookService).createBook(bookArgumentCaptor.capture());

        assertEquals(bookOne, bookArgumentCaptor.getValue());
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("It should update a book")
    public void updateBookTest() throws BookNotFoundException {
        long id = 1;
        Book bookOne = new Book(1L,"First Book", "First Author", 2000, "Disponible");

        doNothing().when(bookService).updateBook(any(), anyLong());
        ResponseEntity<Void> responseEntity = bookController.updateBook(bookOne, id);

        verify(bookService).updateBook(bookArgumentCaptor.capture(), longArgumentCaptor.capture());
        assertEquals(bookOne, bookArgumentCaptor.getValue());
        assertEquals(id, longArgumentCaptor.getValue());

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("It should return the list of all the books")
    public void getAllBooksTest() {
        Book bookOne = new Book(1L,"First Book", "First Author", 2000, "Disponible");
        Book bookTwo = new Book(2L,"Second Book", "Second Author", 2000, "Disponible");

        List<Book> listAllBooks = Arrays.asList(bookOne, bookTwo);
        given(bookService.getAllBooks()).willReturn(listAllBooks);

        List<Book> actualList = bookController.getAllBooks();
        verify(bookService).getAllBooks();

        assertNotNull(actualList);
        assertEquals(2, actualList.size());
    }

    @Test
    @DisplayName("It should return a book by its id")
    public void getBookByIdTest() throws BookNotFoundException {
        Book bookOne = new Book(1L,"First Book", "First Author", 2000, "Disponible");

        given(bookService.getBookById(bookOne.getId())).willReturn(bookOne);
        Book capturedBook = bookController.getBookById(bookOne.getId());

        verify(bookService).getBookById(longArgumentCaptor.capture());
        assertEquals(bookOne.getId(), longArgumentCaptor.getValue());

        assertNotNull(capturedBook);
        assertEquals(capturedBook, bookOne);
    }

    @Test
    @DisplayName("It should return the list of available books")
    public void getBooksByStatusTest() {
        Book bookOne = new Book(1L,"First Book", "First Author", 2000, "Disponible");
        Book bookTwo = new Book(2L,"Second Book", "Second Author", 2000, "Disponible");

        List<Book> listAvailableBooks = Arrays.asList(bookOne, bookTwo);
        given(bookService.getBooksByStatus("DISPONIBLE")).willReturn(listAvailableBooks);

        List<Book> actualList = bookController.getBooksByStatus("DISPONIBLE");
        verify(bookService).getBooksByStatus("DISPONIBLE");

        assertNotNull(actualList);
        assertEquals(2, actualList.size());
    }

    @Test
    @DisplayName("It should delete a book")
    public void deleteBookByIdTest() throws BookNotFoundException {
        Book bookOne = new Book(1L,"First Book", "First Author", 2000, "Disponible");

        doNothing().when(bookService).deleteBookById(anyLong());
        bookController.deleteBookById(bookOne.getId());

        verify(bookService).deleteBookById(longArgumentCaptor.capture());
        assertEquals(bookOne.getId(), longArgumentCaptor.getValue());

        verify(bookService).deleteBookById(bookOne.getId());
    }
}
