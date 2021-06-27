package com.biblioteca.big.service;

import com.biblioteca.big.model.Book;
import com.biblioteca.big.repository.BookRepository;
import org.assertj.core.api.OptionalAssert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
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
    void itShouldInsertBook() {
        //given
        Book book = new Book("Frutillas", "Juana Gomez", 2020,
                "DISPONIBLE", null);
        //when
        bookRepositoryUnderTest.save(book);
        //then
        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepositoryUnderTest).save(bookArgumentCaptor.capture());
        Book capturedBook = bookArgumentCaptor.getValue();
        assertThat(capturedBook).isEqualTo(book);
    }

    @Test
    @Disabled
    void itShouldUpdateExistsBook() {
    }

    @Test
    @Disabled
    void ItShouldGetBooksAvailableByStatus() {
        //when
        bookRepositoryUnderTest.findByStatus("DISPONIBLE", Sort.by("title"));
        //then
        verify(bookRepositoryUnderTest).findByStatus("DISPONIBLE", Sort.by("title"));
    }

    @Test
    void itShouldGetExistsBookById() {
        //given
        Book firstBook = new Book("Frutillas", "Juana Gomez", 2020,
                "DISPONIBLE", null);
        firstBook.setId(1);
        Book secondBook = new Book("Frutillas", "Juana Gomez", 2020,
                "DISPONIBLE", null);
        secondBook.setId(2);
        //when
        bookRepositoryUnderTest.findById(1);
        //
        verify(bookRepositoryUnderTest).findById(firstBook.getId());
    }

    @Test
    void itShouldGetAllExistsBooks() {
        //when
        bookRepositoryUnderTest.findAll(Sort.by("title").ascending());
        //then
        verify(bookRepositoryUnderTest).findAll(Sort.by("title").ascending());
    }

    @Test
    @Disabled
    void itShouldDeleteExistsBookById() {
    }
}