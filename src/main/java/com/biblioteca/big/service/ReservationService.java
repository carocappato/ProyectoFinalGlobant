package com.biblioteca.big.service;

import com.biblioteca.big.model.Reservation;
import com.biblioteca.big.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public void updatedReservation(Reservation reservation){
        Reservation updatedReservation = new Reservation();
        reservationRepository.save(updatedReservation);
    }

}
