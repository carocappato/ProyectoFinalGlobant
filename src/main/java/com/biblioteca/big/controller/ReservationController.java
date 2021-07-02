package com.biblioteca.big.controller;

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
    public ResponseEntity<Void> createReservation(@RequestBody Reservation reservation) {
        reservationService.createReservation(reservation);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    //PUT RESERVATION BY ID
    @PutMapping("/update/{id}")
    public ResponseEntity<Void> updateReservationById(@RequestBody Reservation reservation, @PathVariable("id") Long bookId) throws ReservationNotFoundException {
        reservationService.updateReservationById(reservation, bookId);

        return ResponseEntity.ok().build();
    }

    //GET RESERVATION BY ID
    @GetMapping("/getById/{id}")
    public Reservation getReservationById (@PathVariable("id") Long bookId) throws ReservationNotFoundException {
        return reservationService.getReservationById(bookId);
    }

}
