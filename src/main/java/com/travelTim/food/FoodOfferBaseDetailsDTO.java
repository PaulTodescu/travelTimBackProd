package com.travelTim.food;

import com.travelTim.business.BusinessDTO;
import com.travelTim.business.BusinessDTOMapper;
import com.travelTim.business.BusinessEntity;

public class FoodOfferBaseDetailsDTO {
    private Long id;
    private BusinessEntity business;
    private String image;
    private String createdAt;

    public FoodOfferBaseDetailsDTO() {
    }

    public FoodOfferBaseDetailsDTO(Long id, BusinessEntity business, String createdAt) {
        this.id = id;
        this.business = business;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BusinessDTO getBusiness() {
        BusinessDTOMapper mapper = new BusinessDTOMapper();
        return mapper.mapBusinessToDTO(business);
    }

    public void setBusiness(BusinessEntity business) {
        this.business = business;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
