package org.example.aviafly.repository;


import org.example.aviafly.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {
}
