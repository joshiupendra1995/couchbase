package com.uj.couchbase.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class HotelReservation {
    @Id
    private Integer userId;
    private Integer bookingId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private List<RoomDetails> roomDetails;
}
