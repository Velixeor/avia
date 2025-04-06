package org.example.aviafly.dto;


import lombok.Builder;
import org.example.aviafly.entity.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Builder
public record ReservationDTO(
        Integer id,
        PlaceDTO placeDTO,
        UserDTO userDTO,
        ReservationStatus status,
        BigDecimal price,
        LocalDateTime expiresAt,
        LocalDateTime createdAt,
        List<OptionDTO> options
) {
    public Reservation toEntity(Place place, User user, List<Option> optionList) {
        return new Reservation(
                null,
                place,
                user,
                status,
                price,
                expiresAt,
                createdAt,
                optionList
        );
    }

    public static ReservationDTO fromEntity(Reservation reservation) {
        return ReservationDTO.builder()
                .id(reservation.getId())
                .placeDTO(PlaceDTO.fromEntity(reservation.getPlace()))
                .userDTO(UserDTO.fromEntity(reservation.getUser()))
                .status(reservation.getStatus())
                .price(reservation.getPrice())
                .expiresAt(reservation.getExpiresAt())
                .createdAt(reservation.getCreatedAt())
                .options(reservation.getOptions().stream().map(OptionDTO::fromEntity).collect(Collectors.toList()))
                .build();
    }
}
