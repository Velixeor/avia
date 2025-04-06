package org.example.aviafly.controller;


import lombok.RequiredArgsConstructor;
import org.example.aviafly.dto.OptionDTO;
import org.example.aviafly.dto.ReservationDTO;
import org.example.aviafly.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/create")
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) {
        return ResponseEntity.ok(reservationService.createReservation(reservationDTO));
    }

    @PutMapping("/{reservationId}/add-options")
    public ResponseEntity<ReservationDTO> addOptionsToReservation(
            @PathVariable Integer reservationId,
            @RequestBody List<OptionDTO> options) {
        return ResponseEntity.ok(reservationService.addOptionsToReservation(reservationId, options));
    }

    @PutMapping("/{reservationId}/confirm-payment")
    public ResponseEntity<ReservationDTO> confirmPayment(@PathVariable Integer reservationId) {
        return ResponseEntity.ok(reservationService.confirmPayment(reservationId));
    }

}
