package org.example.aviafly.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.aviafly.dto.FlightDTO;
import org.example.aviafly.dto.PlaceDTO;
import org.example.aviafly.entity.Flight;
import org.example.aviafly.repository.FlightRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@Slf4j
@Service
@RequiredArgsConstructor
public class FlightService {
    private final FlightRepository flightRepository;
    private final PlaceService placeService;

    @Transactional
    public FlightDTO createFlight(FlightDTO flightDTO){
        Flight createFlight=flightRepository.save(flightDTO.toEntity());
        createPlaceToFlight(createFlight);
        return FlightDTO.fromEntity(createFlight);
    }

    private void createPlaceToFlight(Flight flight){
        for(int i=0;i<20;i++){
            placeService.createPlace(flight, PlaceDTO.builder().name("A"+i).basePrice(BigDecimal.valueOf(100.50)).build());
        }
    }
}
