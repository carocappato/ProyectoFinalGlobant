package com.biblioteca.big.repository;

import com.biblioteca.big.model.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class BookRepositoryTest {
    @Autowired private BookRepository bookRepositoryUnderTest;
    @Autowired private ReservationRepository reservationRepositoryUnderTest;
    @Autowired private UserRepository userRepositoryUnderTest;

    @AfterEach
    public void tearDown() {
        bookRepositoryUnderTest.deleteAll();
        reservationRepositoryUnderTest.deleteAll();
        userRepositoryUnderTest.deleteAll();
    }

    @Test
    @DisplayName("It should check if findByStatus method works for available books")
    public void findByAvailableStatusTest() {
        Book book = new Book("Frutillas","Juana Gomez",2020,"Disponible");
        bookRepositoryUnderTest.save(book);

        List<Book> availableBooks = new ArrayList<>();
        availableBooks.add(book);

        List<Book> expected = bookRepositoryUnderTest.findByStatus("Disponible", Sort.by("title"));
        assertThat(expected).isEqualTo(availableBooks);
    }

    @Test
    @DisplayName("It should check if findByStatus method works for available books")
    public void findByReservedStatusTest() {
        Book book = new Book("Naranjas","Juana Gomez",2020,"Reservado");
        bookRepositoryUnderTest.save(book);

        List<Book> reservedBooks = new ArrayList<>();
        reservedBooks.add(book);

        List<Book> expected = bookRepositoryUnderTest.findByStatus("Reservado", Sort.by("title"));
        assertThat(expected).isEqualTo(reservedBooks);
    }

    @Test
    @DisplayName("It should check if a book already exists in database")
    public void findByTitleAndAuthorTest() {
        Book book = new Book("Frutillas","Juana Gomez",2020,"DISPONIBLE");
        bookRepositoryUnderTest.save(book);

        Book expected = bookRepositoryUnderTest.findByTitleAndAuthor("Frutillas", "Juana Gomez");

        assertThat(expected).isEqualTo(book);
    }
}