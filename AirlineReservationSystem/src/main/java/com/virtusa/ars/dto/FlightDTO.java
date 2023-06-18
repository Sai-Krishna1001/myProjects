package com.virtusa.ars.dto;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTO {
    private String flightId;
    private Airline airlineName;
    private String departure;
    private String arrival;
    private String duration;
    private BigDecimal price;
}
