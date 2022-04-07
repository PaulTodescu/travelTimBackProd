package com.travelTim.lodging;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LodgingOfferUtilityDAO extends JpaRepository<LodgingOfferUtilityEntity, Long> {

    Optional<LodgingOfferUtilityEntity> findLodgingOfferUtilityEntityById(Long id);

    Optional<LodgingOfferUtilityEntity> findLodgingOfferUtilityEntityByName(String name);

    void deleteLodgingOfferUtilityEntityById(Long id);

}
