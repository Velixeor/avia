package org.example.aviafly.service;


import lombok.RequiredArgsConstructor;
import org.example.aviafly.entity.Flight;
import org.example.aviafly.entity.Place;
import org.example.aviafly.entity.ReservationStatus;
import org.example.aviafly.exception.NotFoundException;
import org.example.aviafly.repository.PlaceRepository;
import org.example.aviafly.repository.ReservationRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Service
@RequiredArgsConstructor
public class PricingService {

    private final PlaceRepository placeRepository;
    private static final int TOTAL_SEATS=30;

    public BigDecimal calculatePrice(Integer placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new NotFoundException(Place.class, placeId));

        Flight flight = place.getFlight();
        int bookedSeats = placeRepository.countByFlightIdAndIsBooked(flight.getId(), true);

        BigDecimal basePrice = place.getBasePrice();
        BigDecimal demandFactor = calculateDemandFactor(bookedSeats);
        BigDecimal timeFactor = calculateTimeFactor(flight.getDepartureDate());

        return basePrice.multiply(demandFactor).multiply(timeFactor).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateDemandFactor(int bookedSeats) {
        double occupancyRate = (double) bookedSeats / TOTAL_SEATS;
        return BigDecimal.valueOf(1 + occupancyRate);
    }

    private BigDecimal calculateTimeFactor(LocalDateTime departureTime) {
        long hoursToDeparture = ChronoUnit.HOURS.between(LocalDateTime.now(), departureTime);
        if (hoursToDeparture > 48) {
            return BigDecimal.valueOf(1.0);
        } else if (hoursToDeparture > 24) {
            return BigDecimal.valueOf(1.2);
        } else {
            return BigDecimal.valueOf(1.5);
        }
    }
}
