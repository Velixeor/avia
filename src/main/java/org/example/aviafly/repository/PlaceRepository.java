package org.example.aviafly.repository;


import org.example.aviafly.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PlaceRepository extends JpaRepository<Place, Integer> {
    Integer countByFlightIdAndIsBooked(Integer flightId,Boolean isBooked);
}
