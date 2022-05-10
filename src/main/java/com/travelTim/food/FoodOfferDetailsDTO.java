package com.travelTim.food;

import com.travelTim.business.BusinessDTOMapper;
import com.travelTim.business.BusinessDetailsDTO;
import com.travelTim.business.BusinessEntity;

import java.util.Set;

public class FoodOfferDetailsDTO {
    private Long id;
    private String description;
    private BusinessEntity business;
    private Set<FoodMenuCategory> foodMenuCategories;

    public FoodOfferDetailsDTO() {
    }

    public FoodOfferDetailsDTO(Long id, String description, BusinessEntity business,
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

    public BusinessDetailsDTO getBusiness() {
        BusinessDTOMapper mapper = new BusinessDTOMapper();
        return mapper.mapBusinessToDetailsDTO(this.business);
    }

    public void setBusiness(BusinessEntity business) {
        this.business = business;
    }

    public Set<FoodMenuCategory> getFoodMenuCategories() {
        return foodMenuCategories;
    }

    public void setFoodMenuCategories(Set<FoodMenuCategory> foodMenuCategories) {
        this.foodMenuCategories = foodMenuCategories;
    }
}
