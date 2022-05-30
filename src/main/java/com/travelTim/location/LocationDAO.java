package com.travelTim.location;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationDAO extends JpaRepository<LocationEntity, Long> {

    Optional<LocationEntity> findLocationEntityById(Long id);

    void deleteLocationEntityById(Long id);

}
