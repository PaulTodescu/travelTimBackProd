package com.travelTim.food;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodMenuCategoryDAO extends JpaRepository<FoodMenuCategory, Long> {

    Optional<FoodMenuCategory> findFoodMenuCategoryById(Long id);

    Optional<FoodMenuCategory> findFoodMenuCategoryByName(String name);

    void deleteFoodMenuCategoryById(Long id);

}
