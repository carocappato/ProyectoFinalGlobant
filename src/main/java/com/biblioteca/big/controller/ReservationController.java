package com.biblioteca.big.controller;

import com.biblioteca.big.model.Reservation;
import com.biblioteca.big.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/reservations")
@RestController
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable Long id){
        Optional<Reservation> reservation = reservationRepository.findById(id);
        return reservation.get();
        //TODO: EXCEPCION SI NO EXISTE EL ID
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateReservation (@RequestBody Reservation reservation,
                                                     @PathVariable Long id){
        Optional<Reservation> reservationOptional = reservationRepository.findById(id);

        if(!reservationOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        reservation.setId(id);
        reservationRepository.save(reservation);

        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public void newReservation (@RequestBody Reservation reservation){
                reservationRepository.save(reservation);
                //TODO: VALIDACION
    }


}
