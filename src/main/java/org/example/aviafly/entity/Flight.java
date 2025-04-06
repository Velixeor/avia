package org.example.aviafly.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "flight", schema = "airport")
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "where_to_fly_from", nullable = false)
    private String whereToFlyFrom;

    @Column(name = "where_to_fly", nullable = false)
    private String whereToFly;

    @Column(name = "departure_date", nullable = false)
    private LocalDateTime departureDate;
}
