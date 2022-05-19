package com.travelTim.lodging;

import com.travelTim.currency.Currency;
import com.travelTim.location.City;

import java.util.Set;

public class LodgingOfferDetailsForReservationDTO {

    private Long id;
    private Float price;
    private Currency currency;
    private Integer nrRooms;
    private Integer nrBathrooms;
    private Integer nrSingleBeds;
    private Integer nrDoubleBeds;
    private Integer floor;
    private String providerName;
    private String providerEmail;
    private String providerPhone;
    private String offerTitle;
    private String address;
    private City city;
    private Set<LodgingOfferUtilityEntity> utilities;

    public LodgingOfferDetailsForReservationDTO() {
    }

    public LodgingOfferDetailsForReservationDTO(Long id, Float price, Currency currency, Integer nrRooms,
                                                Integer nrBathrooms, Integer nrSingleBeds, Integer nrDoubleBeds,
                                                Integer floor, String providerName, String providerEmail,
                                                String providerPhone, String offerTitle, String address,
                                                City city, Set<LodgingOfferUtilityEntity> utilities) {
        this.id = id;
        this.price = price;
        this.currency = currency;
        this.nrRooms = nrRooms;
        this.nrBathrooms = nrBathrooms;
        this.nrSingleBeds = nrSingleBeds;
        this.nrDoubleBeds = nrDoubleBeds;
        this.floor = floor;
        this.providerName = providerName;
        this.providerEmail = providerEmail;
        this.providerPhone = providerPhone;
        this.offerTitle = offerTitle;
        this.address = address;
        this.city = city;
        this.utilities = utilities;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderEmail() {
        return providerEmail;
    }

    public void setProviderEmail(String providerEmail) {
        this.providerEmail = providerEmail;
    }

    public String getProviderPhone() {
        return providerPhone;
    }

    public void setProviderPhone(String providerPhone) {
        this.providerPhone = providerPhone;
    }

    public String getOfferTitle() {
        return offerTitle;
    }

    public void setOfferTitle(String offerTitle) {
        this.offerTitle = offerTitle;
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

    public Set<LodgingOfferUtilityEntity> getUtilities() {
        return utilities;
    }

    public void setUtilities(Set<LodgingOfferUtilityEntity> utilities) {
        this.utilities = utilities;
    }
}
