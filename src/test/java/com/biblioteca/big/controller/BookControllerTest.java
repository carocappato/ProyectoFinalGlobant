package com.biblioteca.big.controller;

import com.biblioteca.big.model.Book;
import com.biblioteca.big.repository.BookRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;
    /*
    @InjectMocks
    private BookController bookController;


    @Test
    @Disabled
    @DisplayName("It should update the given book by its ID and return the expected HTTP response")
    public void updateBook() throws BookNotFoundException {
        long id = 1L;
        Book bookOne = new Book(1L,"First Book", "First Author", 2000, "Disponible", null);
        when(bookRepository.findById(id)).thenReturn(Optional.of(bookOne));
        assertEquals(bookController.updateBook(bookOne,1L).getStatusCode(), HttpStatus.CREATED);
    }
    */

    @Test
    @DisplayName("It checks if the method returns the list of books and the expected HTTP responsee")
    public void getAllBooksTest() throws Exception {
        Book bookOne = new Book(1L,"First Book", "First Author", 2000, "Disponible", null);
        Book bookTwo = new Book(1L,"Second Book", "Second Author", 2000, "Disponible", null);

        List<Book> listAllBooks = Arrays.asList(bookOne, bookTwo);

        given(bookRepository.findAll()).willReturn(listAllBooks);

        mockMvc.perform(get("/books")).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("It checks if the given ID returns the book and the expected HTTP response")
    public void getBookById() throws Exception {
        Book bookOne = new Book(1L,"First Book", "First Author", 2000, "Disponible", null);

        given(bookRepository.findById(bookOne.getId())).willReturn(Optional.of(bookOne));

        mockMvc.perform(get("/books/" + bookOne.getId().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("It checks if the given status returns the list of books and the expected HTTP response")
    public void getBooksByStatus() throws Exception {
        Book bookOne = new Book(1L,"First Book", "First Author", 2000, "Disponible", null);
        Book bookTwo = new Book(1L,"Second Book", "Second Author", 2000, "Disponible", null);

        List<Book> bookList = Arrays.asList(bookOne, bookTwo);

        given(bookRepository.findByStatus("Disponible", Sort.by("title"))).willReturn(bookList);

        mockMvc.perform(get("/books/status/" + bookOne.getBookStatus()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("It checks if the given ID delete the book and returns the expected HTTP response")
    public void deleteBookById() throws Exception {
        Book bookOne = new Book(1L,"First Book", "First Author", 2000, "Disponible", null);

        given(bookRepository.findById(bookOne.getId())).willReturn(Optional.of(bookOne));

        mockMvc.perform(MockMvcRequestBuilders.delete("/books/" + bookOne.getId().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}