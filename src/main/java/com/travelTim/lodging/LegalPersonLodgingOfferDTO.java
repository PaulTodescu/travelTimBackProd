package com.travelTim.lodging;

import com.travelTim.business.BusinessEntity;
import com.travelTim.currency.Currency;

public class LegalPersonLodgingOfferDTO {

    private Long id;
    private Integer nrRooms;
    private Integer nrSingleBeds;
    private Integer nrDoubleBeds;
    private Float price;
    private Currency currency;
    private String image;

    public LegalPersonLodgingOfferDTO() {
    }

    public LegalPersonLodgingOfferDTO(Long id, Integer nrRooms,
                                      Integer nrSingleBeds, Integer nrDoubleBeds,
                                      Float price, Currency currency) {
        this.id = id;
        this.nrRooms = nrRooms;
        this.nrSingleBeds = nrSingleBeds;
        this.nrDoubleBeds = nrDoubleBeds;
        this.price = price;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
