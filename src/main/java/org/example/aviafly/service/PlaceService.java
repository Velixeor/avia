package org.example.aviafly.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.aviafly.dto.PlaceDTO;
import org.example.aviafly.entity.Flight;
import org.example.aviafly.entity.Place;
import org.example.aviafly.exception.BusinessException;
import org.example.aviafly.exception.NotFoundException;
import org.example.aviafly.repository.PlaceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public PlaceDTO createPlace(Flight flight, PlaceDTO placeDTO) {
        return PlaceDTO.fromEntity(placeRepository.save(placeDTO.toEntity(flight, null)));
    }
    public Place getAvailablePlace(Integer placeId) {
        Place place = findPlaceById(placeId);
        validatePlaceNotBooked(place);
        validateFlightNotDeparted(place);
        return place;
    }

    private Place findPlaceById(Integer placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(() -> new NotFoundException(Place.class, placeId));
    }

    private void validatePlaceNotBooked(Place place) {
        if (place.getIsBooked()) {
            throw new BusinessException("Place with ID " + place.getId() + " is already booked.");
        }
    }

    private void validateFlightNotDeparted(Place place) {
        if (place.getFlight().getDepartureDate().isBefore(LocalDateTime.now())) {
            throw new BusinessException("The flight for place ID " + place.getId() + " has already departed.");
        }
    }

    public void markPlaceAsBooked(Place place) {
        place.setIsBooked(true);
        placeRepository.save(place);
    }

}
