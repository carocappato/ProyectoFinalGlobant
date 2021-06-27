package com.biblioteca.big.repository;

import com.biblioteca.big.model.Book;
import com.biblioteca.big.model.Reservation;
import com.biblioteca.big.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    void tearDown(){
        bookRepositoryUnderTest.deleteAll();
        reservationRepositoryUnderTest.deleteAll();
        userRepositoryUnderTest.deleteAll();
    }

    @Test
    void itShouldFindByStatus() {
        //given
        Book book = new Book("Frutillas", "Juana Gomez", 2020,
                "DISPONIBLE", null);
        Book secondBook = new Book("Naranjas", "Juana Gomez", 2020,
                "RESERVADO", null);
        bookRepositoryUnderTest.save(book);
        bookRepositoryUnderTest.save(secondBook);
        List<Book> availableBooks = new ArrayList<>();
        availableBooks.add(book);
        //when
        List<Book> expected = bookRepositoryUnderTest.findByStatus("DISPONIBLE", Sort.by("title"));
        //then
        assertThat(expected).isEqualTo(availableBooks);
    }

    @Test
    void findByTitleAndAuthor() {
        //given
        Book book = new Book("Frutillas", "Juana Gomez", 2020,
                "DISPONIBLE", null);
        Book secondBook = new Book("Naranjas", "Juana Gomez", 2020,
                "RESERVADO", null);
        bookRepositoryUnderTest.save(book);
        bookRepositoryUnderTest.save(secondBook);
        //when
        Book expected = bookRepositoryUnderTest.findByTitleAndAuthor("Frutillas", "Juana Gomez");
        //then
        assertThat(expected).isEqualTo(book);
    }
}