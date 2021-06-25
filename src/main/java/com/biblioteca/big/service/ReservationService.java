package com.biblioteca.big.service;

import com.biblioteca.big.exception.ReservationAlreadyExistsException;
import com.biblioteca.big.exception.ReservationNotFoundException;
import com.biblioteca.big.model.Reservation;
import com.biblioteca.big.repository.ReservationRepository;
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

    //POST RESERVATION
    public void insertReservation (@RequestBody Reservation reservation) throws ReservationAlreadyExistsException{
        Reservation existsReservation = reservationRepository.findReservationByBookId(reservation.getId());
        //TODO VER ESTO DE BUSCAR POR ID DEL LIBRO
        if (existsReservation != null){
            throw new ReservationAlreadyExistsException("El libro ya está reservado");
        }
        reservationRepository.save(reservation);
        //TODO CAMBIAR EL ESTADO AL LIBRO
    }

    //PUT RESERVATION X ID ¿?
    public void updateExistsReservation (@RequestBody Reservation reservation,
                                         @PathVariable Long id)
            throws ReservationNotFoundException {
        Optional<Reservation> existsReservation = reservationRepository.findReservationByBookId(id);
        //TODO VER ESTO DE BUSCAR POR ID DEL LIBRO
        if (!existsReservation.isPresent()){
            throw new ReservationNotFoundException("La reserva no se puede actulizar");
        }
        reservation.setId(id);
        reservationRepository.save(reservation);
        ResponseEntity.noContent().build();
    }

    //GET RESERVATION X ID ¿?
    public Reservation getExistsReservationById(@PathVariable Long id)
            throws ReservationNotFoundException{
        Optional<Reservation> reservation = reservationRepository.findReservationByBookId(id);
        if (!reservation.isPresent()){
            throw new ReservationNotFoundException("La reserva no existe");
        }
        return reservation.get();
    }

}
