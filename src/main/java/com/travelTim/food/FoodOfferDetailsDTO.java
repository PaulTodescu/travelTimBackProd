package com.travelTim.food;

import com.travelTim.business.BusinessDTOMapper;
import com.travelTim.business.BusinessDetailsDTO;
import com.travelTim.business.BusinessEntity;
import com.travelTim.offer.OfferStatus;

import java.util.Set;

public class FoodOfferDetailsDTO {
    private Long id;
    private String description;
    private BusinessEntity business;
    private Set<FoodMenuCategory> foodMenuCategories;
    private OfferStatus status;
    private Long nrViews;

    public FoodOfferDetailsDTO() {
    }

    public FoodOfferDetailsDTO(Long id, String description, BusinessEntity business,
                               Set<FoodMenuCategory> foodMenuCategories, OfferStatus status, Long nrViews) {
        this.id = id;
        this.description = description;
        this.business = business;
        this.foodMenuCategories = foodMenuCategories;
        this.status = status;
        this.nrViews = nrViews;
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

    public OfferStatus getStatus() {
        return status;
    }

    public void setStatus(OfferStatus status) {
        this.status = status;
    }

    public Long getNrViews() {
        return nrViews;
    }

    public void setNrViews(Long nrViews) {
        this.nrViews = nrViews;
    }
}
