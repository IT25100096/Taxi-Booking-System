package org.example.taxiadmin.repository;

import org.example.taxiadmin.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository
        extends JpaRepository<Booking, Long> {
}