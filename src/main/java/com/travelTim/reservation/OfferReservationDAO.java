package com.travelTim.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferReservationDAO extends JpaRepository<OfferReservation, Long> {
}
