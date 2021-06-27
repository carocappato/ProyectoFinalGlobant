package com.biblioteca.big.service;

import com.biblioteca.big.model.Book;
import com.biblioteca.big.repository.BookRepository;
import org.assertj.core.api.OptionalAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@DataJpaTest
@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    @Mock
    private BookRepository bookRepositoryUnderTest;

    @Test
    @DisplayName("It should insert a book in database")
    void itShouldInsertBook() {
        //GIVEN
        Book book = new Book("Frutillas","Juana Gomez",2020,"DISPONIBLE",null);

        //WHEN
        bookRepositoryUnderTest.save(book);

        //THEN
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepositoryUnderTest).save(bookArgumentCaptor.capture());
        Book capturedBook = bookArgumentCaptor.getValue();

        assertThat(capturedBook).isEqualTo(book);
    }

    @Test
    @Disabled
    @DisplayName("It should update existing book")
    void itShouldUpdateExistsBook() {
    }

    @Test
    @Disabled
    @DisplayName("It should get an available book")
    void ItShouldGetBooksAvailableByStatus() {
        //WHEN
        bookRepositoryUnderTest.findByStatus("DISPONIBLE", Sort.by("title"));

        //THEN
        verify(bookRepositoryUnderTest).findByStatus("DISPONIBLE", Sort.by("title"));
    }

    @Test
    @DisplayName("It should get an existing book by its id")
    void itShouldGetExistsBookById() {
        //GIVEN
        Book firstBook = new Book("Frutillas","Juana Gomez",2020,"DISPONIBLE",null);
        firstBook.setId(1L);

        Book secondBook = new Book("Frutillas","Juana Gomez",2020,"DISPONIBLE",null);
        secondBook.setId(2L);

        //WHEN
        bookRepositoryUnderTest.findById(1L);

        //THEN
        verify(bookRepositoryUnderTest).findById(firstBook.getId());
    }

    @Test
    @DisplayName("It should get all existing books")
    void itShouldGetAllExistsBooks() {
        //WHEN
        bookRepositoryUnderTest.findAll(Sort.by("title").ascending());

        //THEN
        verify(bookRepositoryUnderTest).findAll(Sort.by("title").ascending());
    }

    @Test
    @Disabled
    @DisplayName("It should delete an existing book by its id")
    void itShouldDeleteExistsBookById() {
    }
}