package org.example.aviafly.repository;


import org.example.aviafly.entity.Reservation;
import org.example.aviafly.entity.ReservationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByStatusAndExpiresAtBefore(ReservationStatus status, LocalDateTime now);
}
