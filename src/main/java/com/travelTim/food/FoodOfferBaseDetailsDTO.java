package com.travelTim.food;

import com.travelTim.business.BusinessDTO;
import com.travelTim.business.BusinessDTOMapper;
import com.travelTim.business.BusinessEntity;
import com.travelTim.offer.OfferStatus;

public class FoodOfferBaseDetailsDTO {
    private Long id;
    private BusinessEntity business;
    private OfferStatus status;
    private String image;
    private String createdAt;

    public FoodOfferBaseDetailsDTO() {
    }

    public FoodOfferBaseDetailsDTO(Long id, BusinessEntity business, OfferStatus status, String createdAt) {
        this.id = id;
        this.business = business;
        this.status = status;
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

    public OfferStatus getStatus() {
        return status;
    }

    public void setStatus(OfferStatus status) {
        this.status = status;
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
