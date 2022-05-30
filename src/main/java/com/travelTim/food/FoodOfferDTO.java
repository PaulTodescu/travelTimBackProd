package com.travelTim.food;

import com.travelTim.business.BusinessDTOMapper;
import com.travelTim.business.BusinessEntity;
import com.travelTim.business.BusinessForOffersPageDTO;

import java.time.LocalDateTime;
import java.util.Objects;

public class FoodOfferDTO {

    private Long id;
    private BusinessEntity business;
    private LocalDateTime createdAt;
    private String image;

    public FoodOfferDTO() {
    }

    public FoodOfferDTO(Long id, BusinessEntity business, LocalDateTime createdAt) {
        this.id = id;
        this.business = business;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BusinessForOffersPageDTO getBusiness() {
        if (business != null) {
            BusinessDTOMapper mapper = new BusinessDTOMapper();
            return mapper.mapBusinessForOffersPageDTO(business);
        }
        return null;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FoodOfferDTO)) return false;
        FoodOfferDTO that = (FoodOfferDTO) o;
        return getBusiness().equals(that.getBusiness());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBusiness());
    }
}
