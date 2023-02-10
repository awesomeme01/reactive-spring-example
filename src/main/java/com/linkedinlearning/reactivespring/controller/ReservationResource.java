package com.linkedinlearning.reactivespring.controller;

import com.linkedinlearning.reactivespring.model.Reservation;
import com.linkedinlearning.reactivespring.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping(ReservationResource.ROOM_V_1_RESERVATION)
public class ReservationResource {

    public static final String ROOM_V_1_RESERVATION = "/room/v1/reservation/";

    private final ReservationService reservationService;

    @GetMapping(path = "{roomId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Reservation> getReservationById(
            @PathVariable String roomId
    ) {
        return this.reservationService.getReservation(roomId);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Reservation> create(
            @RequestBody final Mono<Reservation> reservationMono
    ) {
        return this.reservationService.createReservation(reservationMono);
    }

    @PutMapping(path = "{roomId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Reservation> update(
            @PathVariable final String roomId,
            @RequestBody final Mono<Reservation> reservationMono
    ) {
        return this.reservationService.updateReservation(roomId, reservationMono);
    }

    @DeleteMapping(path = "{roomId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Boolean> delete(
            @PathVariable final String roomId
    ) {
        return this.reservationService.deleteReservation(roomId);
    }
}
