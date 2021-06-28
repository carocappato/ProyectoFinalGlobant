package com.biblioteca.big.service;

import com.biblioteca.big.controller.BookController;
import com.biblioteca.big.exception.BookNotFoundException;
import com.biblioteca.big.exception.ReservationAlreadyExistsException;
import com.biblioteca.big.exception.ReservationNotFoundException;
import com.biblioteca.big.exception.UserAlreadyExistsException;
import com.biblioteca.big.model.Book;
import com.biblioteca.big.model.Reservation;
import com.biblioteca.big.model.User;
import com.biblioteca.big.repository.BookRepository;
import com.biblioteca.big.repository.ReservationRepository;
import com.biblioteca.big.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    private BookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    //POST RESERVATION
    public void insertReservation(@RequestBody Reservation reservation)
            throws ReservationAlreadyExistsException, BookNotFoundException, UserAlreadyExistsException {

        Optional<Book> book = bookRepository.findById(reservation.getBookId());
        if(book.isEmpty()) {
            throw new BookNotFoundException("No se encontró el ID: " + reservation.getBookId());
        }

        User existsUser = userRepository.findByDocumentNumber(reservation.getUser().getDocumentNumber());
        if (existsUser != null){
            throw new UserAlreadyExistsException("No se puede");
        }

        Reservation existsReservation = reservationRepository.findByBookId(reservation.getBookId());
        if (existsReservation != null){
            throw new ReservationAlreadyExistsException("El libro ya está reservado");
        }

        bookService.getExistsBookById(reservation.getBookId()).setBookStatus("RESERVADO");
        reservationRepository.save(reservation);
    }


    //PUT RESERVATION X BOOKID
    public void updateExistsReservationByBookId (@RequestBody Reservation reservation,
                                         @PathVariable Long bookId)
            throws ReservationNotFoundException {
        Reservation existsReservation = reservationRepository.findByBookId(bookId);
        if (existsReservation == null){
            throw new ReservationNotFoundException("La reserva no existe");
        }
        reservationRepository.save(reservation);
    }

    //GET RESERVATION X BOOKID
    public Reservation getExistsReservationByBookId(@PathVariable Long bookId)
            throws ReservationNotFoundException{
        Reservation existsReservation = reservationRepository.findByBookId(bookId);
        if (existsReservation == null){
            throw new ReservationNotFoundException("La reserva no existe");
        }
        return existsReservation;
    }
}
