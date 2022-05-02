package com.travelTim.food;

import com.travelTim.business.BusinessEntity;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class FoodOfferEditDTO {
    private Long id;
    private String description;
    private BusinessEntity business;
    private Set<FoodMenuCategory> foodMenuCategories;

    public FoodOfferEditDTO() {
    }

    public FoodOfferEditDTO(Long id, String description, BusinessEntity business,
                            Set<FoodMenuCategory> foodMenuCategories) {
        this.id = id;
        this.description = description;
        this.business = business;
        this.foodMenuCategories = foodMenuCategories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BusinessEntity getBusiness() {
        return business;
    }

    public void setBusiness(BusinessEntity business) {
        this.business = business;
    }

    public Set<FoodMenuCategory> getFoodMenuCategories() {
        for (FoodMenuCategory category: this.foodMenuCategories){
            category.setFoodMenuItems(category.getFoodMenuItems().stream()
                    .sorted(Comparator.comparing(FoodMenuItem::getName))
                    .collect(Collectors.toCollection(LinkedHashSet::new)));
        }
        return this.foodMenuCategories.stream()
                .sorted(Comparator.comparing(FoodMenuCategory::getName))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public void setFoodMenuCategories(Set<FoodMenuCategory> foodMenuCategories) {
        this.foodMenuCategories = foodMenuCategories;
    }
}
