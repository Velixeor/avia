package org.example.aviafly.service;


import lombok.RequiredArgsConstructor;
import org.example.aviafly.entity.Reservation;
import org.example.aviafly.entity.ReservationStatus;
import org.example.aviafly.repository.ReservationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;


@Component
@RequiredArgsConstructor
public class ReservationScheduler {

    private final ReservationRepository reservationRepository;

    @Scheduled(fixedRate = 60000)
    public void checkExpiredReservations() {
        List<Reservation> expiredReservations = reservationRepository.findByStatusAndExpiresAtBefore(
                ReservationStatus.BOOKED, LocalDateTime.now());

        for (Reservation reservation : expiredReservations) {
            reservation.setStatus(ReservationStatus.CANCELLED);
            reservation.getPlace().setIsBooked(false);
            reservationRepository.save(reservation);
        }
    }
}
