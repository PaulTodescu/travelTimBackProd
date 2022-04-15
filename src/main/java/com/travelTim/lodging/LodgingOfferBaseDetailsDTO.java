package com.travelTim.lodging;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.travelTim.business.BusinessEntity;
import com.travelTim.currency.Currency;
import com.travelTim.location.City;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LodgingOfferBaseDetailsDTO {

    private Long id;
    @JsonIgnore
    private BusinessEntity business;
    private String title;
    private String address;
    private City city;
    private String image;
    private LocalDateTime createdAt;
    private Float price;
    private Currency currency;
    private Integer nrRooms;
    private Integer nrSingleBeds;
    private Integer nrDoubleBeds;

    public LodgingOfferBaseDetailsDTO() {
    }

    public LodgingOfferBaseDetailsDTO(Long id, BusinessEntity business, String title, String address,
                                      City city, LocalDateTime createdAt, Float price,
                                      Currency currency, Integer nrRooms, Integer nrSingleBeds,
                                      Integer nrDoubleBeds) {
        this.id = id;
        this.business = business;
        this.title = title;
        this.address = address;
        this.city = city;
        this.createdAt = createdAt;
        this.price = price;
        this.currency = currency;
        this.nrRooms = nrRooms;
        this.nrSingleBeds = nrSingleBeds;
        this.nrDoubleBeds = nrDoubleBeds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BusinessEntity getBusiness() {
        return business;
    }

    public void setBusiness(BusinessEntity business) {
        this.business = business;
    }

    public String getBusinessName(){
        if (this.business != null) {
            return this.business.getName();
        }
        return null;
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Integer getNrRooms() {
        return nrRooms;
    }

    public void setNrRooms(Integer nrRooms) {
        this.nrRooms = nrRooms;
    }

    public Integer getNrSingleBeds() {
        return nrSingleBeds;
    }

    public void setNrSingleBeds(Integer nrSingleBeds) {
        this.nrSingleBeds = nrSingleBeds;
    }

    public Integer getNrDoubleBeds() {
        return nrDoubleBeds;
    }

    public void setNrDoubleBeds(Integer nrDoubleBeds) {
        this.nrDoubleBeds = nrDoubleBeds;
    }
}
