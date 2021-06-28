package com.biblioteca.big.controller;

import com.biblioteca.big.exception.BookNotFoundException;
import com.biblioteca.big.exception.ReservationAlreadyExistsException;
import com.biblioteca.big.exception.ReservationNotFoundException;
import com.biblioteca.big.exception.UserAlreadyExistsException;
import com.biblioteca.big.model.Reservation;
import com.biblioteca.big.service.ReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/reservations")
@RestController
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    //POST RESERVATION
    @PostMapping
    public ResponseEntity<Reservation> newReservation(@RequestBody Reservation reservation)
            throws UserAlreadyExistsException, ReservationAlreadyExistsException, BookNotFoundException {

        reservationService.insertReservation(reservation);
        return ResponseEntity.status(201).build();
    }

    //PUT RESERVATION BY ID
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateReservation (@RequestBody Reservation reservation,
                                                     @PathVariable("id") Long id)
            throws ReservationNotFoundException {
        reservationService.updateExistsReservationByBookId(reservation, id);
        return ResponseEntity.noContent().build();
    }


    //GET RESERVATION BY ID
    @GetMapping("/{id}")
    public void getReservationById(@PathVariable("id") Long bookId)
            throws ReservationNotFoundException {
        reservationService.getExistsReservationByBookId(bookId);
    }

}
