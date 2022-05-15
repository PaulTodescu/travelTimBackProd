package com.travelTim.activities;

import com.travelTim.business.BusinessDTO;
import com.travelTim.location.City;
import com.travelTim.offer.OfferStatus;

import java.time.LocalDateTime;

public class ActivityOfferBaseDetailsDTO {
    private Long id;
    private String title;
    private String address;
    private City city;
    private OfferStatus status;
    private BusinessDTO business;
    private LocalDateTime createdAt;
    private String image;

    public ActivityOfferBaseDetailsDTO() {
    }

    public ActivityOfferBaseDetailsDTO(Long id, String title, String address, City city,
                                       OfferStatus status, BusinessDTO business, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.city = city;
        this.status = status;
        this.business = business;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public OfferStatus getStatus() {
        return status;
    }

    public void setStatus(OfferStatus status) {
        this.status = status;
    }

    public BusinessDTO getBusiness() {
        return business;
    }

    public void setBusiness(BusinessDTO business) {
        this.business = business;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
