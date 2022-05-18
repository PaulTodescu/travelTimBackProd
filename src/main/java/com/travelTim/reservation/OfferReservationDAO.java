package com.travelTim.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OfferReservationDAO extends JpaRepository<OfferReservationEntity, Long> {

    Optional<OfferReservationEntity> findOfferReservationEntityById(Long id);

    void deleteOfferReservationEntityById(Long id);

}
