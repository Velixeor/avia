package org.example.aviafly.dto;


import lombok.Builder;
import org.example.aviafly.entity.Flight;

import java.time.LocalDateTime;


@Builder
public record FlightDTO(
        Integer id,
        String whereToFlyFrom,
        String whereToFly,
        LocalDateTime departureDate)
{
    public Flight toEntity() {
        return new Flight(null, whereToFlyFrom, whereToFly, departureDate);
    }

    public static FlightDTO fromEntity(Flight flight) {
        return FlightDTO.builder()
                .id(flight.getId())
                .whereToFlyFrom(flight.getWhereToFlyFrom())
                .whereToFly(flight.getWhereToFly())
                .departureDate(flight.getDepartureDate())
                .build();
    }

}
