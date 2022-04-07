package com.travelTim.food;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodOfferDAO extends JpaRepository<FoodOfferEntity, Long> {

    Optional<FoodOfferEntity> findFoodOfferEntityById(Long id);

}
