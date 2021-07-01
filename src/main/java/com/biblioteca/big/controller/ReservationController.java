package com.biblioteca.big.controller;

import com.biblioteca.big.exception.BookNotFoundException;
import com.biblioteca.big.exception.ReservationNotFoundException;
import com.biblioteca.big.model.Reservation;
import com.biblioteca.big.service.ReservationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/reservations")
@RestController
public class ReservationController {
    @Autowired private ReservationService reservationService;

    //POST RESERVATION
    @PostMapping("/create")
    public void createReservation(@RequestBody Reservation reservation) throws BookNotFoundException {
        reservationService.insertReservation(reservation);

        ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //PUT RESERVATION BY ID
    @PutMapping("/update/{id}")
    public void updateReservation (@RequestBody Reservation reservation,
                                   @PathVariable("id") Long bookId) throws ReservationNotFoundException {
        reservationService.updateExistingReservationByBookId(reservation, bookId);

        ResponseEntity.noContent().build();
    }

    //GET RESERVATION BY ID
    @GetMapping("/getById/{id}")
    public Reservation getReservationById (@PathVariable("id") Long bookId)
            throws ReservationNotFoundException {

        return reservationService.getExistsReservationByBookId(bookId);
    }

}
