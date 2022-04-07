package com.travelTim.attractions;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.travelTim.business.BusinessEntity;
import com.travelTim.location.City;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttractionOfferDTO {

    private Long id;
    private String title;
    private String address;
    private City city;
    private LocalDateTime createdAt;
    private String image;

    public AttractionOfferDTO() {
    }

    public AttractionOfferDTO(Long id, String title, String address, City city, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.city = city;
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
}
