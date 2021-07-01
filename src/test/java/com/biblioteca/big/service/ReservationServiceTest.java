package com.biblioteca.big.service;

import com.biblioteca.big.exception.ReservationNotFoundException;
import com.biblioteca.big.model.Book;
import com.biblioteca.big.model.Reservation;
import com.biblioteca.big.model.User;
import com.biblioteca.big.repository.ReservationRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock private ReservationRepository reservationRepository;
    @InjectMocks private ReservationService reservationServiceUnderTest;
    @Captor ArgumentCaptor<Reservation> reservationArgumentCaptor;
    @Captor private ArgumentCaptor<Long> idArgumentCaptor;

    @Test
    public void updateExistingReservationByBookIdTest() throws ParseException, ReservationNotFoundException {
        Book book = new Book(1L, "Book Name", "Book Author", 2000, "Disponible");
        User user = new User("John", "Doe", 33444555L,"johndoe@gmail.com");

        SimpleDateFormat date = new SimpleDateFormat("dd-mm-yyyy");
        Date startDate = date.parse("10-10-2021");
        Date endDate = date.parse("20-10-2020");

        Reservation reservation = new Reservation(1L, startDate, endDate);
        reservation.setBook(book);
        reservation.setUser(user);

        given(reservationRepository.findById(anyLong())).willReturn(Optional.of(reservation));

        Date newStartDate = date.parse("15-10-2021");
        Reservation updatedReservation = new Reservation(1L, newStartDate, endDate);
        updatedReservation.setBook(book);
        updatedReservation.setUser(user);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(updatedReservation));

        reservationServiceUnderTest.updateExistingReservationById(updatedReservation, reservation.getId());
        verify(reservationRepository).save(updatedReservation);

        assertThat(updatedReservation.getStartDate()).isEqualTo(newStartDate);
    }

    @Test
    public void cantUpdateReservationTest() throws ParseException {
        long id = 2L;
        Book book = new Book(1L, "Book Name", "Book Author", 2000, "Disponible");
        User user = new User("John", "Doe", 33444555L,"johndoe@gmail.com");
        SimpleDateFormat date = new SimpleDateFormat("dd-mm-yyyy");
        Date startDate = date.parse("10-10-2021");
        Date endDate = date.parse("20-10-2020");

        Reservation reservation = new Reservation(1L, startDate, endDate);
        reservation.setBook(book);
        reservation.setUser(user);

        assertThatThrownBy(() -> reservationServiceUnderTest.updateExistingReservationById(reservation, id))
                .isInstanceOf(ReservationNotFoundException.class)
                .hasMessageContaining("La reserva no existe");

        verify(reservationRepository).findById(idArgumentCaptor.capture());
        assertThat(idArgumentCaptor.getValue()).isEqualTo(id);
        verify(reservationRepository, never()).save(any());
    }

    @Test
    public void getExistingReservationByIdTest() throws ParseException, ReservationNotFoundException {
        Book book = new Book(1L, "Book Name", "Book Author", 2000, "Disponible");
        User user = new User("John", "Doe", 33444555L,"johndoe@gmail.com");
        SimpleDateFormat date = new SimpleDateFormat("dd-mm-yyyy");
        Date startDate = date.parse("10-10-2021");
        Date endDate = date.parse("20-10-2020");

        Reservation reservation = new Reservation(1L, startDate, endDate);
        reservation.setBook(book);
        reservation.setUser(user);
        reservationRepository.save(reservation);

        given(reservationRepository.findById(reservation.getId())).willReturn(Optional.of(reservation));
        reservationServiceUnderTest.getExistsReservationById(reservation.getId());

        verify(reservationRepository).findById(idArgumentCaptor.capture());
        assertThat(idArgumentCaptor.getValue()).isEqualTo(reservation.getId());
    }

    @Test
    public void cantGetReservationByIdTest(){
        long id = 1L;

        assertThatThrownBy(() -> reservationServiceUnderTest.getExistsReservationById(id))
                .isInstanceOf(ReservationNotFoundException.class)
                .hasMessageContaining("La reserva no existe");

        verify(reservationRepository).findById(id);
    }
}