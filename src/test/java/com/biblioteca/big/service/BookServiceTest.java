package com.biblioteca.big.service;

import com.biblioteca.big.exception.BookAlreadyExistsException;
import com.biblioteca.big.exception.BookNotFoundException;
import com.biblioteca.big.model.Book;
import com.biblioteca.big.repository.BookRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    /*
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
    @Disabled
    @DisplayName("It should delete an existing book by its id")
    void itShouldDeleteExistsBookById() {
    }
    */

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookServiceUnderTest;

    @Captor
    private ArgumentCaptor<Book> bookArgumentCaptor;

    @Captor
    private ArgumentCaptor<Long> idArgumentCaptor;

    @BeforeEach
    public void setUp(){
        bookServiceUnderTest = new BookService(bookRepository);
    }

    @AfterEach
    public void tearDown() { bookRepository.deleteAll(); }

    @Test
    @DisplayName("It should get a list of all books in database")
    public void getAllBooksTest() {
        //WHEN
        bookServiceUnderTest.getAllBooks();

        //THEN
        verify(bookRepository).findAll(Sort.by("title").ascending());
    }

    @Test
    @DisplayName("It should get a book by a given ID")
    public void getBookByIdTest() throws BookNotFoundException {
        Book book = new Book(1L, "Book Title", "Book Author", 2000, "Disponible", null);

        bookRepository.save(book);

        given(bookRepository.findById(book.getId())).willReturn(Optional.of(book));

        bookServiceUnderTest.getBookById(book.getId());

        verify(bookRepository).findById(idArgumentCaptor.capture());
        assertThat(idArgumentCaptor.getValue()).isEqualTo(book.getId());
    }

    @Test
    @DisplayName("It shouldn't be able to get a book since it doesn't exist")
    public void cantGetBookByIdTest() throws BookNotFoundException{
        long id = 1;
        Optional<Book> book = bookRepository.findById(id);

        assertThatThrownBy(() -> bookServiceUnderTest.getBookById(id))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessageContaining("No se encontró el ID: " + id);

        assertThat(book).isEmpty();
    }


    /*
    @Test
    @DisplayName("It should get a book by a given status")
    public void getBookByAvailableStatusTest(){
        //TODO: TEST AVAILABLE STATUS
    }
    */


    @Test
    @DisplayName("It should add a book to the database")
    public void insertBookTest() throws BookAlreadyExistsException {
        //GIVEN
        Book book = new Book("Book Name", "Book Author", 2000, "Disponible", null);

        //WHEN
        bookServiceUnderTest.insertBook(book);

        //THEN
        bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
        verify(bookRepository).save(bookArgumentCaptor.capture()); //Verifico que el repositorio capture el valor
        Book capturedBook = bookArgumentCaptor.getValue();//Es el libro que va a recibir el service

        assertThat(capturedBook).isEqualTo(book);//Comparo que el libro que le pase y el que declaro sean iguales
    }

    @Test
    @DisplayName("It should throw an exception when adding a book that already exists to the database")
    public void insertExistingBookTest() throws BookAlreadyExistsException {
        //GIVEN
        Book book = new Book("Book Name", "Book Author", 2000, "Disponible", null);

        given(bookRepository.findByTitleAndAuthor(book.getTitle(),book.getAuthor())).willReturn(book);

        //WHEN
        //THEN
        assertThatThrownBy(() -> bookServiceUnderTest.insertBook(book))
                .isInstanceOf(BookAlreadyExistsException.class)
                .hasMessageContaining("El libro ya existe");

        verify(bookRepository, never()).save(any());
    }


    //TODO: TEST DELETE
    /*
    @Test
    public void deleteBookById(){
        //GIVEN
        long id = 2;

    }


    @Test
    public void deleteBookNonExistingByIdTest() {
        //GIVEN
        long id = 1;

        given(bookRepository.findById(id)).willReturn(bookRepository.findById(id));

        assertThatThrownBy(()-> bookServiceUnderTest.deleteBookById(id))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessageContaining("No se encontró el ID: " + id);

        verify(bookRepository, never()).deleteById(any());


    }

    */

}