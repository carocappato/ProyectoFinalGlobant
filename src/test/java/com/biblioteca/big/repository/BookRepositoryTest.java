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
    @Autowired
    private BookRepository bookRepositoryUnderTest;

    @Autowired
    private ReservationRepository reservationRepositoryUnderTest;

    @Autowired
    private UserRepository userRepositoryUnderTest;

    @AfterEach
    public void tearDown(){
        bookRepositoryUnderTest.deleteAll();
        reservationRepositoryUnderTest.deleteAll();
        userRepositoryUnderTest.deleteAll();
    }

    @Test
    @DisplayName("It should check if findByStatus method works for available books")
    public void findByAvailableStatusTest() {
        //GIVEN
        Book book = new Book(
                "Frutillas",
                "Juana Gomez",
                2020,
                "DISPONIBLE");
        Book secondBook = new Book(
                "Naranjas",
                "Juana Gomez",
                2020,
                "RESERVADO");

        bookRepositoryUnderTest.save(book);
        bookRepositoryUnderTest.save(secondBook);

        List<Book> availableBooks = new ArrayList<>();
        availableBooks.add(book);

        //WHEN
        List<Book> expected = bookRepositoryUnderTest.findByStatus("DISPONIBLE", Sort.by("title"));

        //THEN
        assertThat(expected).isEqualTo(availableBooks);
    }

    @Test
    @DisplayName("It should check if findByStatus method works for available books")
    public void findByReservedStatusTest() {
        //GIVEN
        Book book = new Book(
                "Frutillas",
                "Juana Gomez",
                2020,
                "DISPONIBLE");
        Book secondBook = new Book(
                "Naranjas",
                "Juana Gomez",
                2020,
                "RESERVADO");

        bookRepositoryUnderTest.save(book);
        bookRepositoryUnderTest.save(secondBook);

        List<Book> availableBooks = new ArrayList<>();
        availableBooks.add(secondBook);

        //WHEN
        List<Book> expected = bookRepositoryUnderTest.findByStatus("RESERVADO", Sort.by("title"));

        //THEN
        assertThat(expected).isEqualTo(availableBooks);
    }

    @Test
    @DisplayName("It should check if a book already exists in database")
    public void findByTitleAndAuthorTest() {
        //GIVEN
        Book book = new Book(
                "Frutillas",
                "Juana Gomez",
                2020,
                "DISPONIBLE");
        Book secondBook = new Book(
                "Naranjas",
                "Juana Gomez",
                2020,
                "RESERVADO");

        bookRepositoryUnderTest.save(book);
        bookRepositoryUnderTest.save(secondBook);

        //WHEN
        Book expected = bookRepositoryUnderTest.findByTitleAndAuthor("Frutillas", "Juana Gomez");

        //THEN
        assertThat(expected).isEqualTo(book);
    }
}