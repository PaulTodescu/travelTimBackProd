package com.travelTim.business;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.travelTim.activities.ActivityOfferEntity;
import com.travelTim.attractions.AttractionOfferEntity;
import com.travelTim.food.FoodOfferEntity;
import com.travelTim.location.City;
import com.travelTim.lodging.LegalPersonLodgingOfferEntity;

import java.util.HashSet;
import java.util.Set;

public class BusinessForOffersPageDTO {

    private Long id;
    private String name;
    private String address;
    private City city;
    @JsonIgnore
    private Set<LegalPersonLodgingOfferEntity> lodgingOffers = new HashSet<>();
    @JsonIgnore
    private FoodOfferEntity foodOffer;
    @JsonIgnore
    private Set<AttractionOfferEntity> attractionOffers = new HashSet<>();
    @JsonIgnore
    private Set<ActivityOfferEntity> activityOffers = new HashSet<>();

    public BusinessForOffersPageDTO() {
    }

    public BusinessForOffersPageDTO(Long id, String name, String address, City city,
                                    Set<LegalPersonLodgingOfferEntity> lodgingOffers,
                                    FoodOfferEntity foodOffer,  Set<AttractionOfferEntity> attractionOffers,
                                    Set<ActivityOfferEntity> activityOffers) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.lodgingOffers = lodgingOffers;
        this.foodOffer = foodOffer;
        this.attractionOffers = attractionOffers;
        this.activityOffers = activityOffers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Set<LegalPersonLodgingOfferEntity> getLodgingOffers() {
        return lodgingOffers;
    }

    public void setLodgingOffers(Set<LegalPersonLodgingOfferEntity> lodgingOffers) {
        this.lodgingOffers = lodgingOffers;
    }

    public FoodOfferEntity getFoodOffer() {
        return foodOffer;
    }

    public void setFoodOffer(FoodOfferEntity foodOffer) {
        this.foodOffer = foodOffer;
    }
}
