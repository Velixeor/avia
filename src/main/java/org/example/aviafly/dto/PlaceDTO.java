package org.example.aviafly.dto;


import lombok.Builder;
import org.example.aviafly.entity.Flight;
import org.example.aviafly.entity.Place;
import org.example.aviafly.entity.User;

import java.math.BigDecimal;


@Builder
public record PlaceDTO(
        Integer id,
        String name,
        Boolean isBooked,
        BigDecimal basePrice,
        FlightDTO flightDTO,
        UserDTO userDTO

) {
    public Place toEntity(Flight flight, User user) {
        return new Place(null,
                flight,
                name,
                basePrice,
                user,
                isBooked
        );

    }

    public static PlaceDTO fromEntity(Place place) {
        return PlaceDTO.builder()
                .id(place.getId())
                .flightDTO(FlightDTO.fromEntity(place.getFlight()))
                .isBooked(place.getIsBooked())
                .userDTO(UserDTO.fromEntity(place.getUser()))
                .basePrice(place.getBasePrice())
                .name(place.getName())
                .build();
    }
}
