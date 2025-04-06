package org.example.aviafly.service;


import lombok.RequiredArgsConstructor;
import org.example.aviafly.dto.OptionDTO;
import org.example.aviafly.dto.ReservationDTO;
import org.example.aviafly.entity.*;
import org.example.aviafly.exception.BusinessException;
import org.example.aviafly.exception.NotFoundException;
import org.example.aviafly.repository.ReservationRepository;
import org.example.aviafly.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PlaceService placeService;
    private final UserRepository userRepository;
    private final OptionService optionService;
    private final PricingService pricingService;

    @Transactional
    public ReservationDTO createReservation(ReservationDTO reservationDTO) {
        Place place = placeService.getAvailablePlace(reservationDTO.placeDTO().id());
        User user = getUserById(reservationDTO.userDTO().id());
        placeService.markPlaceAsBooked(place);
        ReservationDTO updatedReservationDTO = updateReservationPrice(reservationDTO, pricingService.calculatePrice(place.getId()));
        return saveReservationAndGetDTO(updatedReservationDTO.toEntity(place, user, null));
    }

    private ReservationDTO updateReservationPrice(ReservationDTO reservationDTO, BigDecimal price) {
        return new ReservationDTO(
                reservationDTO.id(),
                reservationDTO.placeDTO(),
                reservationDTO.userDTO(),
                reservationDTO.status(),
                price,
                reservationDTO.expiresAt(),
                reservationDTO.createdAt(),
                reservationDTO.options()
        );
    }

    private User getUserById(Integer userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(User.class, userId));
    }

    @Transactional
    public ReservationDTO addOptionsToReservation(Integer reservationId, List<OptionDTO> optionDTOs) {
        Reservation reservation = getBookedReservation(reservationId);
        List<Option> options = getAvailableOptions(optionDTOs);
        addOptionsToReservation(reservation, options);
        return saveReservationAndGetDTO(reservation);
    }

    private List<Option> getAvailableOptions(List<OptionDTO> optionDTOs) {
        return optionDTOs.stream()
                .map(optionDTO -> optionService.getAvailableOptionById(optionDTO.id()))
                .collect(Collectors.toList());
    }

    private void addOptionsToReservation(Reservation reservation, List<Option> options) {
        reservation.getOptions().addAll(options);
    }

    private ReservationDTO saveReservationAndGetDTO(Reservation reservation) {
        Reservation saved = reservationRepository.save(reservation);
        return ReservationDTO.fromEntity(saved);
    }

    @Transactional
    public void cancelReservation(Integer reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException(Reservation.class, reservationId));
        reservation.setStatus(ReservationStatus.CANCELLED);
        reservationRepository.save(reservation);
        Place place = reservation.getPlace();
        placeService.markPlaceAsBooked(place);
    }

    private Reservation getBookedReservation(Integer reservationId) {
        Reservation reservation = findReservationById(reservationId);
        validateReservationIsBooked(reservation);
        validateReservationIsNotExpired(reservation);
        return reservation;
    }

    private Reservation findReservationById(Integer reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException(Reservation.class, reservationId));
    }

    private void validateReservationIsBooked(Reservation reservation) {
        if (reservation.getStatus() != ReservationStatus.BOOKED) {
            throw new BusinessException("Cannot add options to a reservation that is not in BOOKED status.");
        }
    }

    private void validateReservationIsNotExpired(Reservation reservation) {
        if (reservation.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new BusinessException("Cannot modify a reservation that has expired.");
        }
    }

    @Transactional
    public ReservationDTO confirmPayment(Integer reservationId) {
        Reservation reservation = getBookedReservation(reservationId);
        validateOptionsAvailability(reservation);
        markReservationAsPaid(reservation);
        return saveReservationAndGetDTO(reservation);
    }

    private void validateOptionsAvailability(Reservation reservation) {
        boolean hasUnavailableOptions = reservation.getOptions().stream()
                .anyMatch(option -> !option.getAvailable());

        if (hasUnavailableOptions) {
            throw new BusinessException("Some options are no longer available. Payment cannot be processed.");
        }
    }

    private void markReservationAsPaid(Reservation reservation) {
        reservation.setStatus(ReservationStatus.PAID);
    }
}
