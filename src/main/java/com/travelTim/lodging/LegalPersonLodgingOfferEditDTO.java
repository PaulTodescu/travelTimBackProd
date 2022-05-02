package com.travelTim.lodging;

import com.travelTim.business.BusinessEntity;
import com.travelTim.currency.Currency;

import java.util.Set;

public class LegalPersonLodgingOfferEditDTO {
    private Long id;
    private BusinessEntity business;
    private Integer nrRooms;
    private Integer nrBathrooms;
    private Integer nrSingleBeds;
    private Integer nrDoubleBeds;
    private Integer floor;
    private Float price;
    private Currency currency;
    private String description;
    private Set<LodgingOfferUtilityEntity> utilities;

    public LegalPersonLodgingOfferEditDTO() {
    }

    public LegalPersonLodgingOfferEditDTO(Long id, BusinessEntity business, Integer nrRooms,
                                          Integer nrBathrooms, Integer nrSingleBeds, Integer nrDoubleBeds,
                                          Integer floor, Float price, Currency currency,
                                          String description, Set<LodgingOfferUtilityEntity> utilities) {
        this.id = id;
        this.business = business;
        this.nrRooms = nrRooms;
        this.nrBathrooms = nrBathrooms;
        this.nrSingleBeds = nrSingleBeds;
        this.nrDoubleBeds = nrDoubleBeds;
        this.floor = floor;
        this.price = price;
        this.currency = currency;
        this.description = description;
        this.utilities = utilities;
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

    public Integer getNrRooms() {
        return nrRooms;
    }

    public void setNrRooms(Integer nrRooms) {
        this.nrRooms = nrRooms;
    }

    public Integer getNrBathrooms() {
        return nrBathrooms;
    }

    public void setNrBathrooms(Integer nrBathrooms) {
        this.nrBathrooms = nrBathrooms;
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

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<LodgingOfferUtilityEntity> getUtilities() {
        return utilities;
    }

    public void setUtilities(Set<LodgingOfferUtilityEntity> utilities) {
        this.utilities = utilities;
    }
}
