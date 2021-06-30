package com.biblioteca.big.controller;

import com.biblioteca.big.exception.BookNotFoundException;
import com.biblioteca.big.exception.ReservationAlreadyExistsException;
import com.biblioteca.big.exception.ReservationNotFoundException;
import com.biblioteca.big.exception.UserAlreadyExistsException;
import com.biblioteca.big.model.Book;
import com.biblioteca.big.model.Reservation;
import com.biblioteca.big.service.ReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/reservations")
@RestController
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    //POST RESERVATION
    @PostMapping
    public ResponseEntity<Reservation> insertReservation(@RequestBody Reservation reservation)
            throws BookNotFoundException {
        reservationService.insertReservation(reservation);

        return ResponseEntity.status(201).build();
    }

    //PUT RESERVATION BY ID
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateReservation (@RequestBody Reservation reservation,
                                                     @PathVariable("id") Long bookId)
            throws ReservationNotFoundException {
        reservationService.updateExistsReservationByBookId(reservation, bookId);
        return ResponseEntity.noContent().build();
    }


    //GET RESERVATION BY ID
    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable("id") Long bookId)
            throws ReservationNotFoundException {
        return reservationService.getExistsReservationByBookId(bookId);
    }

}
