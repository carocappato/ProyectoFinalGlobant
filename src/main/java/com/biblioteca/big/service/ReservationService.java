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
import org.springframework.beans.factory.annotation.Value;
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
        //book.setReservation(reservation); //TODO: ver si se puede setear el id de la reserva en la tabla de libros
        reservation.setUser(user);

        reservationRepository.save(reservation);
    }

    //PUT RESERVATION X BOOKID
    public void updateExistsReservationByBookId (@RequestBody Reservation reservation,
                                                 @PathVariable Long bookId) throws ReservationNotFoundException {
        /*
        Reservation existsReservation = reservationRepository.findByBookId(bookId);

        if (existsReservation == null){
            throw new ReservationNotFoundException("La reserva no existe");
        }
        reservationRepository.save(reservation);*/
    }

    //GET RESERVATION X BOOKID
    public void getExistsReservationByBookId(@PathVariable Long bookId) throws ReservationNotFoundException {
        /*

        Reservation existsReservation = reservationRepository.findByBookId(bookId);
        if (existsReservation == null){
            throw new ReservationNotFoundException("La reserva no existe");
        }
        return existsReservation;
        */
    }
}
