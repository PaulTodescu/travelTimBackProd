package com.travelTim.food;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodMenuItemDAO extends JpaRepository<FoodMenuItem, Long> {

    Optional<FoodMenuItem> findFoodMenuItemById(Long id);

    Optional<FoodMenuItem> findFoodMenuItemByName(String name);

    Optional<FoodMenuItem> findFoodMenuItemByNameAndWeightAndPrice(String name, Double weight, Double price);

    void deleteFoodMenuItemById(Long id);

}
