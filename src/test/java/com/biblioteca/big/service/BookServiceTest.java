package com.biblioteca.big.service;

import com.biblioteca.big.exception.BookAlreadyExistsException;
import com.biblioteca.big.exception.BookNotFoundException;
import com.biblioteca.big.model.Book;
import com.biblioteca.big.repository.BookRepository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.data.domain.Sort;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.floatThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

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
    public void insertExistingBookTest() {
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

    @Test
    @DisplayName("It should update a book by it's given ID")
    public void updateBookTest() throws BookNotFoundException{
        Book book = new Book(1L, "Book Title", "Book Author", 2000, "Disponible", null);
        bookRepository.save(book);

        given(bookRepository.findById(book.getId())).willReturn(Optional.of(book));
        bookServiceUnderTest.updateBook(book, book.getId());
        //verify ??
    }

    @Test
    @DisplayName("It should not update a book because it does not exist")
    public void cantUpdateBookTest() throws BookNotFoundException{
        long id = 2;
        Book book = new Book(1L, "Book Title", "Book Author", 2000, "Disponible", null);

        assertThatThrownBy(() -> bookServiceUnderTest.updateBook(book, id))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessageContaining("El libro no existe");

        verify(bookRepository).findById(idArgumentCaptor.capture());
        assertThat(idArgumentCaptor.getValue()).isEqualTo(id);

        verify(bookRepository, never()).save(any());
    }

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
        //GIVEN
        Book book = new Book(1L, "Book Title", "Book Author", 2000, "Disponible", null);
        bookRepository.save(book);

        given(bookRepository.findById(book.getId())).willReturn(Optional.of(book));
        bookServiceUnderTest.getBookById(book.getId());

        verify(bookRepository).findById(idArgumentCaptor.capture());
        assertThat(idArgumentCaptor.getValue()).isEqualTo(book.getId());
    }

    @Test
    @DisplayName("It shouldn't be able to get a book since it doesn't exist")
    public void cantGetBookByIdTest(){
        long id = 1;

        assertThatThrownBy(() -> bookServiceUnderTest.getBookById(id))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessageContaining("No se encontró el ID: " + id);

        verify(bookRepository).findById(id);
    }

    @Test
    @DisplayName("It should get available books")
    public void getBookByAvailableStatusTest(){
        //WHEN
        Book book = new Book(1L, "Book Title", "Book Author", 2000, "Disponible", null);
        bookRepository.save(book);

        bookServiceUnderTest.getBooksByStatus("Disponible");

        //THEN
        verify(bookRepository).findByStatus("Disponible", Sort.by("title").ascending());
    }

    @Test
    @DisplayName("It should get reserved books")
    public void getBookByReservedStatusTest(){
        //WHEN
        Book book = new Book(1L, "Book Title", "Book Author", 2000, "Reservado", null);
        bookRepository.save(book);

        bookServiceUnderTest.getBooksByStatus("Reservado");

        //THEN
        verify(bookRepository).findByStatus("Reservado", Sort.by("title").ascending());
    }

    @Test
    @DisplayName("It should delete a book by its ID")
    public void deleteBookByIdTest() throws BookNotFoundException {
        Book book = new Book(1L, "Book Title", "Book Author", 2000, "Disponible", null);
        bookRepository.save(book);

        given(bookRepository.findById(book.getId())).willReturn(Optional.of(book));
        bookServiceUnderTest.deleteBookById(book.getId());
        verify(bookRepository).findById(idArgumentCaptor.capture());

        assertThat(idArgumentCaptor.getValue()).isEqualTo(book.getId());
    }

    @Test
    @DisplayName("It can't delete the book id since it doesn't exist")
    public void cantDeleteBookByIdTest() {
        long id = 1;

        assertThatThrownBy(() -> bookServiceUnderTest.deleteBookById(id))
                .isInstanceOf(BookNotFoundException.class)
                .hasMessageContaining("No se encontró el ID: " + id);

        verify(bookRepository).findById(id);
    }
}