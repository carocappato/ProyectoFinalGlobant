package com.biblioteca.big.service;

import com.biblioteca.big.exception.BookNotFoundException;
import com.biblioteca.big.exception.ReservationNotFoundException;
import com.biblioteca.big.model.Book;
import com.biblioteca.big.model.Reservation;
import com.biblioteca.big.model.User;
import com.biblioteca.big.repository.BookRepository;
import com.biblioteca.big.repository.ReservationRepository;
import com.biblioteca.big.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    //POST RESERVATION
    public void insertReservation(Reservation reservation) throws BookNotFoundException {
        Optional<Book> bookOptional = bookRepository.findById(reservation.getBook().getId());
        Optional<User> userOptional = userRepository.findById(reservation.getUser().getId());

        Book book = bookOptional.get();
        User user = userOptional.get();

        reservation.setBook(book);
        book.setBookStatus("Reservado");
        reservation.setUser(user);

        reservationRepository.save(reservation);
    }

    //PUT RESERVATION X ID
    public void updateExistsReservationByBookId (@RequestBody Reservation reservation,
                                                 @PathVariable Long id) throws ReservationNotFoundException {

        Optional<Reservation> existsReservation = reservationRepository.findById(id);

        if (existsReservation.isEmpty()){
            throw new ReservationNotFoundException("La reserva no existe");
        }

        reservation.setId(id);
        reservationRepository.save(reservation);
    }

    //GET RESERVATION X ID
    public Reservation getExistsReservationByBookId(@PathVariable Long id)
            throws ReservationNotFoundException {

        Optional<Reservation> existsReservation = reservationRepository.findById(id);
        if (existsReservation.isEmpty()){
            throw new ReservationNotFoundException("La reserva no existe");
        }
        return existsReservation.get();
    }
}
