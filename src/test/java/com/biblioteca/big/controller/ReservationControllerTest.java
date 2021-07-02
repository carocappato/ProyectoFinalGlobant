package com.biblioteca.big.controller;

import com.biblioteca.big.exception.ReservationNotFoundException;
import com.biblioteca.big.model.Book;
import com.biblioteca.big.model.Reservation;
import com.biblioteca.big.model.User;
import com.biblioteca.big.service.ReservationService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest {
    @InjectMocks private ReservationController reservationController;
    @Mock ReservationService reservationService;
    @Captor private ArgumentCaptor<Reservation> reservationArgumentCaptor;
    @Captor private ArgumentCaptor<Long> longArgumentCaptor;

    @Test
    @DisplayName("It should create a reservation")
    public void createReservationTest() throws ParseException {
        Book book = new Book(1L, "Book Name", "Book Author", 2000, "Disponible");
        User user = new User("John", "Doe", 33444555L,"johndoe@gmail.com");
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate = date.parse("10-10-2021");
        Date endDate = date.parse("20-10-2021");

        Reservation reservation = new Reservation(1L, startDate, endDate);
        reservation.setBook(book);
        reservation.setUser(user);

        doNothing().when(reservationService).createReservation(any());
        ResponseEntity<Void> responseEntity = reservationController.createReservation(reservation);

        verify(reservationService).createReservation(reservationArgumentCaptor.capture());

        assertEquals(reservation, reservationArgumentCaptor.getValue());
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("It should update a reservation by its ID")
    public void updateReservationByIdTest() throws ParseException, ReservationNotFoundException {
        long id = 1L;
        Book book = new Book(1L, "Book Name", "Book Author", 2000, "Disponible");
        User user = new User("John", "Doe", 33444555L,"johndoe@gmail.com");
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate = date.parse("10-10-2021");
        Date endDate = date.parse("20-10-2021");

        Reservation reservation = new Reservation(1L, startDate, endDate);
        reservation.setBook(book);
        reservation.setUser(user);

        doNothing().when(reservationService).updateReservationById(any(), anyLong());
        ResponseEntity<Void> responseEntity = reservationController.updateReservationById(reservation, id);

        verify(reservationService).updateReservationById(reservationArgumentCaptor.capture(), longArgumentCaptor.capture());
        assertEquals(reservation, reservationArgumentCaptor.getValue());
        assertEquals(id, longArgumentCaptor.getValue());

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("It should get a reservation by its ID")
    public void getReservationByIdTest() throws ParseException, ReservationNotFoundException {
        Book book = new Book(1L, "Book Name", "Book Author", 2000, "Disponible");
        User user = new User("John", "Doe", 33444555L,"johndoe@gmail.com");
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate = date.parse("10-10-2021");
        Date endDate = date.parse("20-10-2021");

        Reservation reservation = new Reservation(1L, startDate, endDate);
        reservation.setBook(book);
        reservation.setUser(user);

        given(reservationService.getReservationById(reservation.getId())).willReturn(reservation);
        Reservation capturedReservation = reservationController.getReservationById(reservation.getId());

        verify(reservationService).getReservationById(longArgumentCaptor.capture());
        assertEquals(reservation.getId(), longArgumentCaptor.getValue());

        assertNotNull(capturedReservation);
        assertEquals(capturedReservation, reservation);
    }
}