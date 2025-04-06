package org.example.aviafly.controller;


import lombok.RequiredArgsConstructor;
import org.example.aviafly.dto.FlightDTO;
import org.example.aviafly.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/flight")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;

    @PostMapping("/create")
    public ResponseEntity<FlightDTO> createFlight(@RequestBody  FlightDTO flightDTO){
        return new ResponseEntity<>(flightService.createFlight(flightDTO), HttpStatus.OK);
    }

}
