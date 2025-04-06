package org.example.aviafly.service;


import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class ArchiveScheduler {

    private final JdbcTemplate jdbcTemplate;

    @Scheduled(cron = "0 0 2 * * ?")
    @Transactional
    public void archiveOldData() {
        archiveOldReservations();
        archiveOldPlaces();
    }

    private void archiveOldReservations() {
        String insertReservations = """
            INSERT INTO airport.reservation_archive(id, place_id, user_id, status, expires_at, created_at, price)
            SELECT id, place_id, user_id, status, expires_at, created_at, price
            FROM airport.reservation
            WHERE created_at < NOW() - INTERVAL '1 year'
        """;

        String deleteReservations = """
            DELETE FROM airport.reservation
            WHERE created_at < NOW() - INTERVAL '1 year'
        """;

        jdbcTemplate.update(insertReservations);
        jdbcTemplate.update(deleteReservations);
    }

    private void archiveOldPlaces() {
        String insertPlaces = """
            INSERT INTO airport.place_archive(id, flight_id, name, user_id, base_price, is_booked)
            SELECT p.id, p.flight_id, p.name, p.user_id, p.base_price, p.is_booked
            FROM airport.place p
            JOIN airport.flight f ON p.flight_id = f.id
            WHERE f.departure_date < NOW() - INTERVAL '1 year'
        """;

        String deletePlaces = """
            DELETE FROM airport.place
            WHERE id IN (
                SELECT p.id
                FROM airport.place p
                JOIN airport.flight f ON p.flight_id = f.id
                WHERE f.departure_date < NOW() - INTERVAL '1 year'
            )
        """;

        jdbcTemplate.update(insertPlaces);
        jdbcTemplate.update(deletePlaces);
    }
}
