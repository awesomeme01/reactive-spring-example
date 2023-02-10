package com.linkedinlearning.reactivespring.service;

import com.linkedinlearning.reactivespring.model.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReactiveMongoOperations reactiveMongoOperations;

    @Override
    public Mono<Reservation> getReservation(String id) {
        return this.reactiveMongoOperations.findById(id, Reservation.class);
    }

    @Override
    public Mono<Reservation> createReservation(Mono<Reservation> reservationMono) {
        return this.reactiveMongoOperations.save(reservationMono);
    }

    @Override
    public Mono<Reservation> updateReservation(String id, Mono<Reservation> reservationMono) {
        return reservationMono.flatMap(reservation ->
                this.reactiveMongoOperations.findAndModify(
                        Query.query(Criteria.where("id").is(id)),
                        Update.update("price", reservation.getPrice()),
                        Reservation.class
                ).flatMap(result -> {
                    result.setPrice(reservation.getPrice());
                    return Mono.just(result);
                })
        );
    }

    @Override
    public Mono<Boolean> deleteReservation(String id) {
        return this.reactiveMongoOperations.remove(Query.query(Criteria.where("id").is(id)), Reservation.class)
                .flatMap(deleteResult -> Mono.just(deleteResult.wasAcknowledged()));
    }
}
