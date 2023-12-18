package com.uj.couchbase.controller;

import com.uj.couchbase.entity.HotelReservation;
import com.uj.couchbase.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
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

    @GetMapping("/rooms/{roomType}")
    public Flux<HotelReservation> findByRoomType(@PathVariable String roomType){
        return hotelRepository.findByRoomType(roomType);
    }

    @GetMapping("/rooms/count/{count}")
    public Flux<HotelReservation> findRoomsByCount(@PathVariable int count){
        return hotelRepository.findRoomsByCount(count);
    }
}
