package org.example.aviafly.repository;


import jakarta.persistence.LockModeType;
import org.example.aviafly.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PlaceRepository extends JpaRepository<Place, Integer> {
    Integer countByFlightIdAndIsBooked(Integer flightId,Boolean isBooked);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Place p WHERE p.id = :id AND p.isBooked = false")
    Optional<Place> findAvailablePlaceForUpdate(@Param("id") Integer id);
}
