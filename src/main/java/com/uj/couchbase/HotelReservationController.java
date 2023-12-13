package com.uj.couchbase;

import com.uj.couchbase.entity.HotelReservation;
import com.uj.couchbase.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/hotel")
@RequiredArgsConstructor
public class HotelReservationController {

    private final HotelRepository hotelRepository;

    @PostMapping
    public Mono<HotelReservation> addReservation(@RequestBody HotelReservation hotelReservation){
        return hotelRepository.save(hotelReservation);
    }

    @GetMapping("/{userId}")
    public Mono<HotelReservation> findReservationForUser(@PathVariable Integer userId){
        return hotelRepository.findById(userId);
    }
}
