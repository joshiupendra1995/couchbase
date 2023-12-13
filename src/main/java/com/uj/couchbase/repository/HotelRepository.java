package com.uj.couchbase.repository;

import com.uj.couchbase.entity.HotelReservation;
import org.springframework.data.couchbase.repository.ReactiveCouchbaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends ReactiveCouchbaseRepository<HotelReservation, Integer> {
}
