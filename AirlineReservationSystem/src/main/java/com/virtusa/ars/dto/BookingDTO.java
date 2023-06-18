package com.virtusa.ars.dto;

import java.math.BigDecimal;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDTO {
    private String bookingId;
    private String userId;
    private String flightId;
    private Airline airlineName;
    private String passengerName;
    private BigDecimal price;
    private LocalDateTime createdAt;
}
