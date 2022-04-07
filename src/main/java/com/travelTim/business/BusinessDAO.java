package com.travelTim.business;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessDAO extends JpaRepository<BusinessEntity, Long> {

    Optional<BusinessEntity> findBusinessEntityById(Long id);

    void deleteBusinessEntityById(Long id);
}
