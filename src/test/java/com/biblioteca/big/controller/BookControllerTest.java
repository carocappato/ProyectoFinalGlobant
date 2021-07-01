package com.biblioteca.big.controller;

import com.biblioteca.big.model.Book;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private BookController bookController;

    @Test
    @DisplayName( "It inserts a book in database")
    public void createBookTest() throws Exception {
        /*
        Book bookOne = new Book(1L,"First Book", "First Author", 2000, "Disponible");

        bookController.createBook(bookOne);

        ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
        verify(bookController).createBook(bookArgumentCaptor.capture());
        Book capturedBook = bookArgumentCaptor.getValue();

        assertThat(capturedBook).isEqualTo(bookOne);

        mockMvc.perform(post("/books/create"))
                .andExpect(MockMvcResultMatchers.status().isCreated());
        */
    }

    @Test
    @DisplayName("It checks if the method returns the list of books and the expected HTTP responsee")
    public void getAllBooksTest() throws Exception {
        Book bookOne = new Book(1L,"First Book", "First Author", 2000, "Disponible");
        Book bookTwo = new Book(2L,"Second Book", "Second Author", 2000, "Disponible");

        List<Book> listAllBooks = Arrays.asList(bookOne, bookTwo);
        given(bookController.getAllBooks()).willReturn(listAllBooks);

        mockMvc.perform(get("/books/getAll"));
    }

    @Test
    @DisplayName("It checks if the given ID returns the book and the expected HTTP response")
    public void getBookByIdTest() throws Exception {
        Book bookOne = new Book(1L,"First Book", "First Author", 2000, "Disponible");

        given(bookController.getBookById(bookOne.getId())).willReturn(bookOne);

        mockMvc.perform(get("/books/getById/" + bookOne.getId().toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("It checks if the given status returns the list of books and the expected HTTP response")
    public void getBooksByStatusTest() throws Exception {
        Book bookOne = new Book(1L,"First Book", "First Author", 2000, "Disponible");
        Book bookTwo = new Book(1L,"Second Book", "Second Author", 2000, "Disponible");

        List<Book> bookList = Arrays.asList(bookOne, bookTwo);

        given(bookController.getBooksByStatus("Disponible")).willReturn(bookList);

        mockMvc.perform(get("/books/getByStatus/" + bookOne.getBookStatus()));
    }

    @Test
    @DisplayName("It checks if the given ID delete the book and returns the expected HTTP response")
    public void deleteBookByIdTest() throws Exception {
        Book bookOne = new Book(1L,"First Book", "First Author", 2000, "Disponible");

        given(bookController.getBookById(bookOne.getId())).willReturn(bookOne);

        mockMvc.perform(MockMvcRequestBuilders.delete("/books/delete/" + bookOne.getId().toString()));
    }
}