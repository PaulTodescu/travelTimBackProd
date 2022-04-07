package com.travelTim.lodging;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhysicalPersonLodgingOfferDAO extends JpaRepository<PhysicalPersonLodgingOfferEntity, Long> {

    Optional<PhysicalPersonLodgingOfferEntity> findPhysicalPersonLodgingOfferEntityById(Long id);

    void deletePhysicalPersonLodgingOfferEntityById(Long id);

}
