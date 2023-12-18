package com.uj.couchbase.repository;

import com.uj.couchbase.entity.HotelReservation;
import org.springframework.data.couchbase.repository.Collection;
import org.springframework.data.couchbase.repository.Query;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;
import org.springframework.data.couchbase.repository.Scope;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
@Scope("hotels")
@Collection("HotelReservation")
public interface HotelRepository extends ReactiveCouchbaseRepository<HotelReservation, Integer> {

    @Query("#{#n1ql.selectEntity} WHERE EVERY rd IN roomDetails SATISFIES rd.roomType = $1 END")
    Flux<HotelReservation> findByRoomType(String roomType);

    @Query("#{#n1ql.selectEntity} WHERE ARRAY_LENGTH(roomDetails) = $1")
    Flux<HotelReservation> findRoomsByCount(int count);
}
