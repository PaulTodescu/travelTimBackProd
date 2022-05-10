package com.travelTim.food;

import java.util.Set;

public class FoodOfferIdMenuImageDTO {

    private Long id;
    private String image;
    private Set<FoodMenuCategory> foodMenuCategories;

    public FoodOfferIdMenuImageDTO() {
    }

    public FoodOfferIdMenuImageDTO(Long id, Set<FoodMenuCategory> foodMenuCategories) {
        this.id = id;
        this.foodMenuCategories = foodMenuCategories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<FoodMenuCategory> getFoodMenuCategories() {
        return foodMenuCategories;
    }

    public void setFoodMenuCategories(Set<FoodMenuCategory> foodMenuCategories) {
        this.foodMenuCategories = foodMenuCategories;
    }

}
