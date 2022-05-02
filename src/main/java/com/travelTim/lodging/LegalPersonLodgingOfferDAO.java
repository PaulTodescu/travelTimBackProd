package com.travelTim.lodging;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LegalPersonLodgingOfferDAO extends JpaRepository<LegalPersonLodgingOfferEntity, Long> {

    Optional<LegalPersonLodgingOfferEntity> findLegalPersonLodgingOfferEntityById(Long id);
}
