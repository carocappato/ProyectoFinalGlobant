package com.biblioteca.big.service;

import com.biblioteca.big.exception.ReservationNotFoundException;
import com.biblioteca.big.model.Book;
import com.biblioteca.big.model.Reservation;
import com.biblioteca.big.model.User;
import com.biblioteca.big.repository.ReservationRepository;

import org.junit.jupiter.api.DisplayName;
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
public class ReservationServiceTest {
    @Mock private ReservationRepository reservationRepository;
    @InjectMocks private ReservationService reservationServiceUnderTest;
    @Captor private ArgumentCaptor<Long> idArgumentCaptor;

    @Test
    @DisplayName("It should update a reservation by it's id")
    public void updateReservationByIdTest() throws ParseException, ReservationNotFoundException {
        Book book = new Book(1L, "Book Name", "Book Author", 2000, "Disponible");
        User user = new User("John", "Doe", 33444555L,"johndoe@gmail.com");

        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate = date.parse("10-10-2021");
        Date endDate = date.parse("20-10-2021");

        Reservation reservation = new Reservation(1L, startDate, endDate);
        reservation.setBook(book);
        reservation.setUser(user);

        given(reservationRepository.findById(anyLong())).willReturn(Optional.of(reservation));

        Date newStartDate = date.parse("15-10-2021");
        Reservation updatedReservation = new Reservation(1L, newStartDate, endDate);
        updatedReservation.setBook(book);
        updatedReservation.setUser(user);

        when(reservationRepository.findById(1L)).thenReturn(Optional.of(updatedReservation));

        reservationServiceUnderTest.updateReservationById(updatedReservation, reservation.getId());
        verify(reservationRepository).save(updatedReservation);

        assertThat(updatedReservation.getStartDate()).isEqualTo(newStartDate);
    }

    @Test
    @DisplayName("It shouldn't update the reservation since that ID doesn't exist")
    public void cantUpdateReservationByIdTest() throws ParseException {
        long id = 2;
        Book book = new Book(1L, "Book Name", "Book Author", 2000, "Disponible");
        User user = new User("John", "Doe", 33444555L,"johndoe@gmail.com");
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate = date.parse("10-10-2021");
        Date endDate = date.parse("20-10-2021");

        Reservation reservation = new Reservation(1L, startDate, endDate);
        reservation.setBook(book);
        reservation.setUser(user);

        assertThatThrownBy(() -> reservationServiceUnderTest.updateReservationById(reservation, id))
                .isInstanceOf(ReservationNotFoundException.class)
                .hasMessageContaining("La reserva no existe");

        verify(reservationRepository).findById(idArgumentCaptor.capture());
        assertThat(idArgumentCaptor.getValue()).isEqualTo(id);
        verify(reservationRepository, never()).save(any());
    }

    @Test
    @DisplayName("It should get a reservation by it's id")
    public void getReservationByIdTest() throws ParseException, ReservationNotFoundException {
        Book book = new Book(1L, "Book Name", "Book Author", 2000, "Disponible");
        User user = new User("John", "Doe", 33444555L,"johndoe@gmail.com");
        SimpleDateFormat date = new SimpleDateFormat("dd-MM-yyyy");
        Date startDate = date.parse("10-10-2021");
        Date endDate = date.parse("20-10-2021");

        Reservation reservation = new Reservation(1L, startDate, endDate);
        reservation.setBook(book);
        reservation.setUser(user);
        reservationRepository.save(reservation);

        given(reservationRepository.findById(reservation.getId())).willReturn(Optional.of(reservation));
        reservationServiceUnderTest.getReservationById(reservation.getId());

        verify(reservationRepository).findById(idArgumentCaptor.capture());
        assertThat(idArgumentCaptor.getValue()).isEqualTo(reservation.getId());
    }

    @Test
    @DisplayName("It shouldn't get the reservation since that ID doesn't exist")
    public void cantGetReservationByIdTest(){
        long id = 1;

        assertThatThrownBy(() -> reservationServiceUnderTest.getReservationById(id))
                .isInstanceOf(ReservationNotFoundException.class)
                .hasMessageContaining("La reserva no existe");

        verify(reservationRepository).findById(id);
    }
}