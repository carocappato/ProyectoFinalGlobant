package com.biblioteca.big.controller;

import com.biblioteca.big.exception.BookAlreadyExistsException;
import com.biblioteca.big.model.Book;
import com.biblioteca.big.service.BookService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {
    @InjectMocks
    private BookService bookServiceUnderTest;

    @Captor
    ArgumentCaptor<Book> bookArgumentCaptor;

    @Test
    @Disabled
    @DisplayName("It should insert a book in database")
    public void insertBookTest() throws BookAlreadyExistsException {
        //GIVEN
        Book book = new Book("Frutillas","Juana Gomez",2020,"Disponible",null);

        //WHEN
        bookServiceUnderTest.insertBook(book);

        //THEN
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
        verify(bookServiceUnderTest).insertBook(bookArgumentCaptor.capture());
        Book capturedBook = bookArgumentCaptor.getValue();

        assertThat(capturedBook).isEqualTo(book);
    }

    @Test
    @Disabled
    void updateBook() {
    }

    @Test
    @Disabled
    void getAllBooks() {
    }

    @Test
    @Disabled
    void getBookById() {
    }

    @Test
    @Disabled
    void getBooksByStatus() {
    }

    @Test
    @Disabled
    void deleteBookById() {
    }
}